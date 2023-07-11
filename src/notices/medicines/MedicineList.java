package notices.medicines;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;
import java.util.TreeMap;
import interfaces.FileHandler;

public class MedicineList implements FileHandler {
    private TreeMap<Integer, Medicine> medicines;
    private Double revenue;
    private Double dueAmount;
    private Integer purchaseCount;

    public MedicineList() {
        this.medicines = new TreeMap<Integer, Medicine>();
        this.revenue = 0.0;
        this.dueAmount = 0.0;
        this.purchaseCount = 0;
    }

    // write to csv file
    public void writer() {
        try {
            FileWriter csvWriter = new FileWriter("medicine_list.csv", true);
            for (Map.Entry<Integer, Medicine> e : medicines.entrySet()) {
                Medicine s = e.getValue();
                csvWriter.append(s.toString());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            System.out.println("MedicineList.writer()");
        } finally {
            medicines.clear();
        }
    }

    // read from csv file
    public void reader() {
        try {
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader("medicine_list.csv"));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                medicines.put(Integer.valueOf(data[0]), new Medicine(Integer.valueOf(data[0]), data[1],
                        Double.valueOf(data[2]), Integer.valueOf(data[3])));
            }
            csvReader.close();
        } catch (Exception e) {
            System.out.println("MedicineList.reader()");
        }
    }

    // setters for revenue, due amount, and number of purchases done.
    public void setRevenue(Double amt) {
        this.revenue += amt;
    }

    public void setDueAmount(Double amt) {
        this.dueAmount += amt;
    }

    public void setPurchaseCount(Integer count) {
        this.purchaseCount += count;
    }

    // get a particular medicine information
    public Medicine getMedicine(Integer itemId) {
        reader();
        Medicine med = medicines.get(itemId);
        writer();
        return med;
    }

    // update medicine information
    public void updateMedicine(Integer itemId, Medicine med) {
        reader();
        medicines.replace(itemId, med);
        writer();
    }
}
