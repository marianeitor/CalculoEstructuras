package com.example.nico.calculoestructuras.xmlparser;

import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.CargaEnBarra;
import com.example.nico.calculoestructuras.Negocio.CargaEnNudo;
import com.example.nico.calculoestructuras.Negocio.Conectividad;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;


public class XmlHandler extends DefaultHandler {

    private static final int IN_NUDO = 0;
    private static final int IN_BARRA = 1;
    private static final int IN_CONEC = 2;
    private static final int IN_VINCULO = 3;
    private static final int IN_CARGANUDO = 4;
    private static final int IN_CARGABARRA = 5;

    private int estado;

    private Nudo currentNudo;
    private Barra currentBarra;
    private Conectividad currentConec;
    private Vinculo currentVinculo;
    private CargaEnNudo currentCargaNudo;
    private CargaEnBarra currentCargaBarra;
    private StringBuilder currentString;
    private Ejercicio ejercicio;

    private ArrayList<Nudo> nudosEncontrados;
    private ArrayList<Barra> barrasEncontradas;
    private ArrayList<Conectividad> conectividadesEncontradas;
    private ArrayList<Vinculo> vinculosEncontrados;
    private ArrayList<CargaEnNudo> cargaNudoEncontradas;
    private ArrayList<CargaEnBarra> cargaBarraEncontradas;


    @Override
    public void startDocument() throws SAXException {
        nudosEncontrados = new ArrayList<>();
        barrasEncontradas = new ArrayList<>();
        conectividadesEncontradas = new ArrayList<>();
        vinculosEncontrados = new ArrayList<>();
        cargaNudoEncontradas = new ArrayList<>();
        cargaBarraEncontradas = new ArrayList<>();
        currentString = new StringBuilder();
    }

    @Override
    public void endDocument() throws SAXException {
        ejercicio = new Ejercicio(nudosEncontrados, barrasEncontradas, conectividadesEncontradas,
                vinculosEncontrados, cargaNudoEncontradas, cargaBarraEncontradas);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (localName) {
            case "nudo":
                currentNudo = new Nudo();
                estado = IN_NUDO;
                break;
            case "barra":
                currentBarra = new Barra();
                estado = IN_BARRA;
                break;
            case "conectividad":
                currentConec = new Conectividad();
                estado = IN_CONEC;
                break;
            case "vinculo":
                currentVinculo = new Vinculo();
                estado = IN_VINCULO;
                break;
            case "carganudo":
                currentCargaNudo = new CargaEnNudo();
                estado = IN_CARGANUDO;
                break;
            case "cargabarra":
                currentCargaBarra = new CargaEnBarra();
                estado = IN_CARGABARRA;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (estado) {
            case IN_NUDO:
                switch (localName) {
                    case "nudo":
                        nudosEncontrados.add(currentNudo);
                        break;
                    case "idnudo":
                        currentNudo.setnOrden(Integer.parseInt(currentString.toString()));
                        break;
                    case "coordx":
                        currentNudo.setX(Double.parseDouble(currentString.toString()));
                        break;
                    case "coordy":
                        currentNudo.setY(Double.parseDouble(currentString.toString()));
                        break;
                }
                break;
            case IN_BARRA:
                switch (localName) {
                    case "barra":
                        barrasEncontradas.add(currentBarra);
                        break;
                    case "idbarra":
                        currentBarra.setNumOrden(Integer.parseInt(currentString.toString()));
                        break;
                    case "elasticidad":
                        currentBarra.setElasticidad(Double.parseDouble(currentString.toString()));
                        break;
                    case "inercia":
                        currentBarra.setInercia(Double.parseDouble(currentString.toString()));
                        break;
                    case "area":
                        currentBarra.setArea(Double.parseDouble(currentString.toString()));
                        break;
                }
                break;
            case IN_CONEC:
                switch (localName) {
                    case "conectividad":
                        conectividadesEncontradas.add(currentConec);
                        break;
                    case "barraconectada":
                        currentConec.setNumBarra(Integer.parseInt(currentString.toString()));
                        break;
                    case "niconec":
                        currentConec.setNumNudoInicial(Integer.parseInt(currentString.toString()));
                        break;
                    case "nfconec":
                        currentConec.setNumNudoFinal(Integer.parseInt(currentString.toString()));
                        break;
                }
                break;
            case IN_VINCULO:
                switch (localName) {
                    case "vinculo":
                        vinculosEncontrados.add(currentVinculo);
                        break;
                    case "nudovinculado":
                        currentVinculo.setNumNudo(Integer.parseInt(currentString.toString()));
                        break;
                    case "restx":
                        currentVinculo.setRestX(Double.parseDouble(currentString.toString()));
                        break;
                    case "resty":
                        currentVinculo.setRestY(Double.parseDouble(currentString.toString()));
                        break;
                    case "restgiro":
                        currentVinculo.setRestGiro(Double.parseDouble(currentString.toString()));
                        break;
                }
                break;
            case IN_CARGANUDO:
                switch (localName) {
                    case "carganudo":
                        cargaNudoEncontradas.add(currentCargaNudo);
                        break;
                    case "nudoCargado":
                        currentCargaNudo.setNumNudo(Integer.parseInt(currentString.toString()));
                        break;
                    case "cargaenx":
                        currentCargaNudo.setCargaEnX(Double.parseDouble(currentString.toString()));
                        break;
                    case "cargaeny":
                        currentCargaNudo.setCargaEnY(Double.parseDouble(currentString.toString()));
                        break;
                    case "cargaenz":
                        currentCargaNudo.setCargaEnZ(Double.parseDouble(currentString.toString()));
                        break;
                }
                break;
            case IN_CARGABARRA:
                switch (localName) {
                    case "cargabarra":
                        cargaBarraEncontradas.add(currentCargaBarra);
                        break;
                    case "barracargada":
                        currentCargaBarra.setNumBarra(Integer.parseInt(currentString.toString()));
                        break;
                    case "cargadistribuida":
                        currentCargaBarra.setCargaDistribuida(Double.parseDouble(currentString.toString()));
                        break;
                    case "cargapuntualenx":
                        currentCargaBarra.setCargaPuntualEnX(Double.parseDouble(currentString.toString()));
                        break;
                    case "cargapuntualeny":
                        currentCargaBarra.setCargaPuntualEnY(Double.parseDouble(currentString.toString()));
                        break;
                    case "cargapuntualenz":
                        currentCargaBarra.setCargaPuntualEnZ(Double.parseDouble(currentString.toString()));
                        break;
                    case "cargapuntualdistxy":
                        currentCargaBarra.setCargaPuntualDistXY(Double.parseDouble(currentString.toString()));
                        break;
                    case "cargapuntualdistz":
                        currentCargaBarra.setCargaPuntualDistZ(Double.parseDouble(currentString.toString()));
                        break;
                }
                break;
        }
        currentString = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        for (int i = start; i < start+length; i++) {
            currentString.append(ch[i]);
        }
    }

    public Ejercicio getParsedData() {
        return ejercicio;
    }
}
