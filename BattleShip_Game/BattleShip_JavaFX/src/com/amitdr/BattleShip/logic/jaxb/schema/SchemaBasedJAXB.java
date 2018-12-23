package com.amitdr.BattleShip.logic.jaxb.schema;

import com.amitdr.BattleShip.logic.jaxb.schema.generated.BattleShipGame;
import com.amitdr.BattleShip.logic.exceptions.SchemaBasedJAXBException;
import com.amitdr.BattleShip.logic.utils.FileUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;


public class SchemaBasedJAXB
{
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "com.amitdr.BattleShip.logic.jaxb.schema.generated";

    public static BattleShipGame createBattleShipGame(String i_FilePath) throws SchemaBasedJAXBException
    {
        FileInputStream inputStream;
        BattleShipGame gameSchema = null;

        try
        {
            File xmlFile = new File(i_FilePath);

            if(!xmlFile.exists())
            {
                throw new SchemaBasedJAXBException("File not loaded successfully! - File not found.");
            }
            if(!xmlFile.isFile() || !isXmlFile(i_FilePath))
            {
                throw new SchemaBasedJAXBException("File not loaded successfully! - Please enter a path to .xml file");
            }

            gameSchema = deserializeFrom(xmlFile);
        }
        catch(NullPointerException e)
        {
            throw new SchemaBasedJAXBException("File not loaded successfully! - No path given.");
        }
        catch(Exception e)
        {
            throw new SchemaBasedJAXBException(e.getMessage());
        }

        return gameSchema;
    }

    private static BattleShipGame deserializeFrom(File in) throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();

        return (BattleShipGame) u.unmarshal(in);
    }

    private static boolean isXmlFile(String path)
    {
        String fileExtension = FileUtils.getExtension(path);
        boolean isXmlFile = false;

        if(fileExtension.toLowerCase().equals("xml"))
        {
            isXmlFile = true;
        }

        return isXmlFile;
    }
  }
