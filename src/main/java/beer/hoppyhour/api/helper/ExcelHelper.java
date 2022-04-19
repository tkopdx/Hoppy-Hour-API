package beer.hoppyhour.api.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import beer.hoppyhour.api.entity.Hop;
import beer.hoppyhour.api.entity.Malt;
import beer.hoppyhour.api.entity.Place;
import beer.hoppyhour.api.entity.Yeast;
import beer.hoppyhour.api.service.PlaceService;

@Service
@Transactional
public class ExcelHelper {

    @Autowired
    PlaceService placeService;

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HOP_HEADERS = { "Hop", "Origin", "Type", "Alpha", "Beta", "Notes" };
    static String[] MALT_HEADERS = { "Grain", "Origin", "Mash", "Color", "Power", "Potential", "Max %", "Notes" };
    static String[] YEAST_HEADERS = { "Name", "Brand", "Type", "Form", "Temp Low (F)", "Temp High (F)", "Attenutation",
            "Flocculation", "Description" };

    static String HOP_SHEET = "hop";
    static String YEAST_SHEET = "yeast";
    static String MALT_SHEET = "table";
    static String PLACE_SHEET = "Sheet1";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<Hop> excelToHops(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(HOP_SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Hop> hops = new ArrayList<Hop>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Hop hop = new Hop();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            if (currentCell.getCellType() == CellType.STRING) {
                                hop.setName(currentCell.getStringCellValue());
                            }
                            break;
                        case 1:
                            if (currentCell.getCellType() == CellType.STRING) {
                                Place place = placeService.getCapitalByCountry(currentCell.getStringCellValue());
                                place.addHop(hop);
                            }
                            break;
                        case 2:
                            if (currentCell.getCellType() == CellType.STRING) {
                                hop.setType(currentCell.getStringCellValue());
                            }

                            break;
                        case 3:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                hop.setAverageAlphaAcidLow(currentCell.getNumericCellValue());
                            }
                            
                            break;
                        case 4:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                hop.setAverageBetaAcidHigh(currentCell.getNumericCellValue());
                            }
                            
                            break;
                        case 5:
                            if (currentCell.getCellType() == CellType.STRING) {
                                hop.setDescription(currentCell.getStringCellValue());
                            }

                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                hops.add(hop);
            }
            workbook.close();
            return hops;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public List<Malt> excelToMalts(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(MALT_SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Malt> malts = new ArrayList<Malt>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Malt malt = new Malt();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            malt.setName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            if (currentCell.getCellType() == CellType.STRING) {
                                Place place = placeService.getCapitalByCountry(currentCell.getStringCellValue());
                                place.addMalt(malt);
                            }

                            break;
                        case 2:
                            if (currentCell.getCellType() == CellType.BOOLEAN) {
                                malt.setMashable(currentCell.getBooleanCellValue());
                            }
                            break;
                        case 3:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                malt.setSrm(currentCell.getNumericCellValue());
                            }
                            
                            break;
                        case 4:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                malt.setPower(currentCell.getNumericCellValue());
                            }
                            
                            break;
                        case 5:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                malt.setPotential(currentCell.getNumericCellValue());
                            }
                            
                            break;
                        case 6:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                malt.setMax(currentCell.getNumericCellValue());
                            }
                            
                            break;
                        case 7:
                            if (currentCell.getCellType() == CellType.STRING) {
                                malt.setDescription(currentCell.getStringCellValue());
                            }

                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                malts.add(malt);
            }
            workbook.close();
            return malts;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public List<Yeast> excelToYeasts(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(YEAST_SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Yeast> yeasts = new ArrayList<Yeast>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Yeast yeast = new Yeast();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            yeast.setName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            if (currentCell.getCellType() == CellType.STRING) {
                                yeast.setBrand(currentCell.getStringCellValue());
                            }
                            break;
                        case 2:
                            if (currentCell.getCellType() == CellType.STRING) {
                                yeast.setType(currentCell.getStringCellValue());
                            }

                            break;
                        case 3:
                            if (currentCell.getCellType() == CellType.STRING) {
                                yeast.setForm(currentCell.getStringCellValue());
                            }

                            break;
                        case 4:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                yeast.setTemperatureLow(
                                    ConversionHelper.fahrenheitToCelsius(currentCell.getNumericCellValue()));
                            }
                            
                            break;
                        case 5:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                yeast.setTemperatureHigh(
                                    ConversionHelper.fahrenheitToCelsius(currentCell.getNumericCellValue()));
                            }
                            
                            break;
                        case 6:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                yeast.setApparentAttenuationHigh(currentCell.getNumericCellValue());
                            }
                            
                            break;
                        case 7:
                            if (currentCell.getCellType() == CellType.STRING) {
                                yeast.setFlocculation(currentCell.getStringCellValue());
                            }

                            break;
                        case 8:
                            if (currentCell.getCellType() == CellType.STRING) {
                                yeast.setDescription(currentCell.getStringCellValue());
                            }

                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                yeasts.add(yeast);
            }
            workbook.close();
            return yeasts;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public List<Place> excelToPlaces(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(PLACE_SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Place> places = new ArrayList<Place>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Place place = new Place();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 1:
                            place.setCity(currentCell.getStringCellValue());
                            break;
                        case 2:
                            place.setLatitude(currentCell.getNumericCellValue());
                            break;
                        case 3:
                            place.setLongitude(currentCell.getNumericCellValue());
                            break;
                        case 4:
                            place.setCountry(currentCell.getStringCellValue());
                            break;
                        case 8:
                            Boolean isCapital = false;
                            if (currentCell.getCellType() == CellType.STRING
                                    && currentCell.getStringCellValue().equals("primary")) {
                                System.out.println("setting is capital to true!");
                                isCapital = true;
                            }
                            place.setIsCapital(isCapital);
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                places.add(place);
            }
            workbook.close();
            return places;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
