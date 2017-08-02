package com.example.nico.calculoestructuras.xmlparser;

import android.content.Context;
import android.util.Xml;
import android.widget.Toast;

import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.CargaEnBarra;
import com.example.nico.calculoestructuras.Negocio.CargaEnNudo;
import com.example.nico.calculoestructuras.Negocio.Conectividad;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Carlos on May 2016.
 */

public class XmlParser {

    private Context _context;
    private Ejercicio ejercicio;

    public XmlParser(Context context) {
        _context = context;
    }

    /**
     * Verifica si existe un ejercicio con el titulo dado.
     * @param tituloEjercicio Titulo del ejercicio a buscar
     * @return boolean
     */
    public boolean existe(String tituloEjercicio) {
        File file = _context.getFileStreamPath(tituloEjercicio + ".xml");
        return (file.exists());
    }

    /**
     * Carga un ejercicio desde el directorio de ejercicios guardados.
     * @param directorioEjercicio Directorio de ejercicios guardados.
     */
    public void cargarEjercicio (String directorioEjercicio) {
        try{
            FileInputStream file2 = _context.openFileInput(directorioEjercicio+".xml");

            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = parserFactory.newSAXParser();

            XMLReader reader = saxParser.getXMLReader();
            XmlHandler xmlHandler = new XmlHandler();
            reader.setContentHandler(xmlHandler);

            InputSource input = new InputSource();
            input.setByteStream(file2);

//          Descomentar para ver lo que viene del File
/*            StringBuilder data = new StringBuilder();
            InputStreamReader isr = new InputStreamReader(file2);
            BufferedReader inRd = new BufferedReader(isr);
            String text = inRd.readLine();
            while(text != null){
                data.append(text);
                data.append("\n");
                text = inRd.readLine();
            }*/

            reader.parse(input);
            ejercicio = xmlHandler.getParsedData();
            DataBaseHelper.getDatabaseInstance(_context).ejercicioToDataBase(ejercicio);
        } catch (ParserConfigurationException e) {
            Toast.makeText(_context, "ParserConfiguration", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (SAXException e) {
            Toast.makeText(_context, "SAXException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(_context, "IOException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * Guarda el ejercicio en formato xml.
     * @param tituloEjercicio Titulo del ejercicio
     */
    public void guardarEjercicio (String tituloEjercicio) {
        try{
            XmlSerializer serializer = Xml.newSerializer();
            OutputStreamWriter fout =
                    new OutputStreamWriter(
                            _context.openFileOutput(tituloEjercicio + ".xml",
                                    Context.MODE_PRIVATE));
            serializer.setOutput(fout);
            ejercicio = DataBaseHelper.getDatabaseInstance(_context).dataBaseToEjercicio();
            serializer.startTag("","ejercicio");

            for (Nudo nudo:ejercicio.nudos) {
                serializer.startTag("","nudo");
                serializer.startTag("","idnudo");
                serializer.text(Integer.toString(nudo.getnOrden()));
                serializer.endTag("","idnudo");
                serializer.startTag("","coordx");
                serializer.text(Double.toString(nudo.getX()));
                serializer.endTag("","coordx");
                serializer.startTag("","coordy");
                serializer.text(Double.toString(nudo.getY()));
                serializer.endTag("","coordy");
                serializer.endTag("","nudo");
            }

            for (Barra barra:ejercicio.barras) {
                serializer.startTag("","barra");
                serializer.startTag("","idbarra");
                serializer.text(Integer.toString(barra.getNumOrden()));
                serializer.endTag("","idbarra");
                serializer.startTag("","elasticidad");
                serializer.text(Double.toString(barra.getElasticidad()));
                serializer.endTag("","elasticidad");
                serializer.startTag("","inercia");
                serializer.text(Double.toString(barra.getInercia()));
                serializer.endTag("","inercia");
                serializer.startTag("","area");
                serializer.text(Double.toString(barra.getArea()));
                serializer.endTag("","area");
                serializer.endTag("","barra");
            }

            for (Conectividad conec:ejercicio.conectividades) {
                serializer.startTag("","conectividad");
                serializer.startTag("","barraconectada");
                serializer.text(Integer.toString(conec.getNumBarra()));
                serializer.endTag("","barraconectada");
                serializer.startTag("","niconec");
                serializer.text(Integer.toString(conec.getNumNudoInicial()));
                serializer.endTag("","niconec");
                serializer.startTag("","nfconec");
                serializer.text(Integer.toString(conec.getNumNudoFinal()));
                serializer.endTag("","nfconec");
                serializer.endTag("","conectividad");
            }

            for (Vinculo vinculo:ejercicio.vinculos) {
                serializer.startTag("","vinculo");
                serializer.startTag("","nudovinculado");
                serializer.text(Integer.toString(vinculo.getNumNudo()));
                serializer.endTag("","nudovinculado");
                serializer.startTag("","restx");
                serializer.text(Double.toString(vinculo.getRestX()));
                serializer.endTag("","restx");
                serializer.startTag("","resty");
                serializer.text(Double.toString(vinculo.getRestY()));
                serializer.endTag("","resty");
                serializer.startTag("","restgiro");
                serializer.text(Double.toString(vinculo.getRestGiro()));
                serializer.endTag("","restgiro");
                serializer.endTag("","vinculo");
            }

            for (CargaEnNudo cNudo:ejercicio.cargaNudo) {
                serializer.startTag("","carganudo");
                serializer.startTag("","nudoCargado");
                serializer.text(Integer.toString(cNudo.getNumNudo()));
                serializer.endTag("","nudoCargado");
                serializer.startTag("","cargaenx");
                serializer.text(Double.toString(cNudo.getCargaEnX()));
                serializer.endTag("","cargaenx");
                serializer.startTag("","cargaeny");
                serializer.text(Double.toString(cNudo.getCargaEnY()));
                serializer.endTag("","cargaeny");
                serializer.startTag("","cargaenz");
                serializer.text(Double.toString(cNudo.getCargaEnZ()));
                serializer.endTag("","cargaenz");
                serializer.endTag("","carganudo");
            }

            for (CargaEnBarra cBarra:ejercicio.cargaBarra) {
                serializer.startTag("","cargabarra");
                serializer.startTag("","barracargada");
                serializer.text(Integer.toString(cBarra.getNumBarra()));
                serializer.endTag("","barracargada");
                serializer.startTag("","cargadistribuida");
                serializer.text(Double.toString(cBarra.getCargaDistribuida()));
                serializer.endTag("","cargadistribuida");
                serializer.startTag("","cargapuntualenx");
                serializer.text(Double.toString(cBarra.getCargaPuntualEnX()));
                serializer.endTag("","cargapuntualenx");
                serializer.startTag("","cargapuntualeny");
                serializer.text(Double.toString(cBarra.getCargaPuntualEnY()));
                serializer.endTag("","cargapuntualeny");
                serializer.startTag("","cargapuntualenz");
                serializer.text(Double.toString(cBarra.getCargaPuntualEnZ()));
                serializer.endTag("","cargapuntualenz");
                serializer.startTag("","cargapuntualdistxy");
                serializer.text(Double.toString(cBarra.getCargaPuntualDistXY()));
                serializer.endTag("","cargapuntualdistxy");
                serializer.startTag("","cargapuntualdistz");
                serializer.text(Double.toString(cBarra.getCargaPuntualDistZ()));
                serializer.endTag("","cargapuntualdistz");
                serializer.endTag("","cargabarra");
            }

            serializer.endTag("","ejercicio");
            serializer.endDocument();
            fout.close();

        } catch (IOException e) {
            Toast.makeText(_context, "IOException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
