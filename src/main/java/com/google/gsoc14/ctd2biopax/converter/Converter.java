package com.google.gsoc14.ctd2biopax.converter;

import org.biopax.paxtools.model.BioPAXFactory;
import org.biopax.paxtools.model.BioPAXLevel;
import org.biopax.paxtools.model.Model;

import java.io.FileInputStream;
import java.io.InputStream;

public abstract class Converter {
    private BioPAXFactory bioPAXFactory = BioPAXLevel.L3.getDefaultFactory();

    public BioPAXFactory getBioPAXFactory() {
        return bioPAXFactory;
    }

    public void setBioPAXFactory(BioPAXFactory bioPAXFactory) {
        this.bioPAXFactory = bioPAXFactory;
    }

    public Model createNewModel() {
        return getBioPAXFactory().createModel();
    }

    public abstract Model convert(InputStream inputStream);
}
