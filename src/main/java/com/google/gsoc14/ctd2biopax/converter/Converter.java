package com.google.gsoc14.ctd2biopax.converter;

import com.google.gsoc14.ctd2biopax.util.CTDUtil;
import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.BioPAXFactory;
import org.biopax.paxtools.model.BioPAXLevel;
import org.biopax.paxtools.model.Model;

import java.io.IOException;
import java.io.InputStream;

public abstract class Converter {
    private BioPAXFactory bioPAXFactory = BioPAXLevel.L3.getDefaultFactory();

    public static String sharedXMLBase = "http://www.ctdbase.org/#";

    public BioPAXFactory getBioPAXFactory() {
        return bioPAXFactory;
    }

    public void setBioPAXFactory(BioPAXFactory bioPAXFactory) {
        this.bioPAXFactory = bioPAXFactory;
    }

    public Model createNewModel() {
        Model model = getBioPAXFactory().createModel();
        model.setXmlBase(getXMLBase());
        return model;
    }

    public <T extends BioPAXElement> T create(Class<T> aClass, String uri) {
        return getBioPAXFactory().create(aClass, completeId(uri));
    }

    public abstract Model convert(InputStream inputStream) throws IOException;

    private String XMLBase = Converter.sharedXMLBase;

    public  String getXMLBase() {
        return XMLBase;
    }

    public void setXMLBase(String sharedXMLBase) {
        XMLBase = sharedXMLBase;
    }

    protected String completeId(String partialId) {
        return getXMLBase() + partialId;
    }
}
