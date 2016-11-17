package org.ctdbase;

import org.ctdbase.converter.CTDChemicalConverter;
import org.ctdbase.converter.CTDGeneConverter;
import org.ctdbase.converter.CTDInteractionConverter;
import org.ctdbase.converter.Converter;
import org.ctdbase.util.CtdUtil;
import org.apache.commons.cli.*;
import org.biopax.paxtools.controller.Merger;
import org.biopax.paxtools.controller.ModelUtils;
import org.biopax.paxtools.io.SimpleIOHandler;
import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.model.level3.EntityReference;
import org.biopax.paxtools.trove.TProvider;
import org.biopax.paxtools.util.BPCollections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class CtdToBiopaxConverter {
    private static Logger log = LoggerFactory.getLogger(CtdToBiopaxConverter.class);
    private static final String helpText = CtdToBiopaxConverter.class.getSimpleName();

    public static void main( String[] args ) {
        final CommandLineParser clParser = new GnuParser();
        Options gnuOptions = new Options();
        gnuOptions
                .addOption("x", "interaction", true, "structured chemical-gene interaction file (XML) [optional]")
                .addOption("g", "gene", true, "CTD gene vocabulary (CSV) [optional]")
                .addOption("c", "chemical", true, "CTD chemical vocabulary (CSV) [optional]")
                .addOption("o", "output", true, "Output (BioPAX file) [required]")
                .addOption("t", "taxonomy", true, "Taxonomy (e.g. '9606' for human) [optional]")
                .addOption("r", "remove-tangling", false, "Remove tangling entities for clean-up [optional]")
        ;

        try {
            CommandLine commandLine = clParser.parse(gnuOptions, args);

            // Interaction file and output file name are required!
            if(!commandLine.hasOption("o")) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp(helpText, gnuOptions);
                System.exit(-1);
            }

            // Memory efficiency fix for huge BioPAX models
            BPCollections.I.setProvider(new TProvider());

            SimpleIOHandler simpleIOHandler = new SimpleIOHandler();
            Merger merger = new Merger(simpleIOHandler.getEditorMap());
            Model finalModel = simpleIOHandler.getFactory().createModel();

            // First convert the interactions
            convertAndMergeFile(commandLine, "x", new CTDInteractionConverter(), merger, finalModel);
            // If we have other files, also merge them to the final model
            convertAndMergeFile(commandLine, "g", new CTDGeneConverter(), merger, finalModel);
            convertAndMergeFile(commandLine, "c", new CTDChemicalConverter(), merger, finalModel);

            if(commandLine.hasOption("r")) {
                Set<BioPAXElement> removed = ModelUtils.removeObjectsIfDangling(finalModel, EntityReference.class);
                log.info("Removed " + removed.size() + " tangling entity references from the model.");
            }

            // Setting the xmlbase
            finalModel.setXmlBase(Converter.sharedXMLBase);
            String outputFile = commandLine.getOptionValue("o");
            log.info("Done with the conversions. Converting the final model to OWL: " + outputFile);
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            //TODO (refs issue #7) remove those all-in-one "pathways" from the final model
            if(commandLine.hasOption("t")) {
                String taxonomy = commandLine.getOptionValue("t");
                log.info("Filtering taxonomy for: " + taxonomy);
                String taxonPathwayUri = finalModel.getXmlBase() + CtdUtil.taxonPathwayId(taxonomy);
                simpleIOHandler.convertToOWL(finalModel, outputStream, taxonPathwayUri);
            } else {
                simpleIOHandler.convertToOWL(finalModel, outputStream);
            }
//            outputStream.close(); //not required
            log.info("All done.");
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp(helpText, gnuOptions);
            System.exit(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void convertAndMergeFile(CommandLine commandLine,
                                     String option,
                                     Converter converter,
                                     Merger merger,
                                     Model finalModel)
            throws IOException
    {
        if(commandLine.hasOption(option)) {
            String fileName = commandLine.getOptionValue(option);
            FileInputStream fis = new FileInputStream(fileName);
            log.info(
                    "Found option '" + option + "'. " +
                            "Using " + converter.getClass().getSimpleName() + " for conversion of file: " + fileName
            );
            Model model = converter.convert(fis);
            merger.merge(finalModel, model);
            fis.close();
        } else {
            log.debug("Couldn't find option '" + option + "'.");
        }
    }
}
