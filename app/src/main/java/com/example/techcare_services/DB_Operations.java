package com.example.techcare_services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DB_Operations extends SQLiteOpenHelper {
    public DB_Operations(@Nullable Context context) {
        super(context, "TechCareServices", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE users (user_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        "email VARCHAR(30)UNIQUE NOT NULL," +
                        "full_name VARCHAR(30) NOT NULL, " +
                        "password_hash TEXT NOT NULL," +
                        "role VARCHAR(10) NOT NULL)";
        db.execSQL(sql);

        sql = "CREATE TABLE customers (customer_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "user_id INTEGER NOT NULL, " +
                "phone VARCHAR(10), " +
                "address VARCHAR(50)," +
                "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE)";
        db.execSQL(sql);

        sql = "CREATE TABLE technicians (technician_id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "user_id INTEGER NOT NULL, " +
                "specialization VARCHAR(30), " +
                "availability_status VARCHAR(20)," +
                "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE)";
        db.execSQL(sql);

        sql = "CREATE TABLE devices (device_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "device_name VARCHAR(20) NOT NULL," +
                "brand VARCHAR(25)," +
                "device_type VARCHAR(20)," +
                "image_res_id INTEGER," +
                "service_icon_res_id INTEGER)";
        db.execSQL(sql);

        sql = "CREATE TABLE customer_saved_devices (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "customer_id INTEGER NOT NULL," +
                "device_id INTEGER NOT NULL," +
                "is_active INTEGER DEFAULT 1," +
                "FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE," +
                "FOREIGN KEY (device_id) REFERENCES devices(device_id) ON DELETE CASCADE)";
        db.execSQL(sql);

        sql = "CREATE TABLE services (service_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "service_name VARCHAR(15)," +
                "description VARCHAR(50)," +
                "price REAL," +
                "device_id INTEGER," +
                "estimated_duration INTEGER," +
                "image_res_id INTEGER," +
                "FOREIGN KEY (device_id) REFERENCES devices(device_id))";
        db.execSQL(sql);

        sql = "CREATE TABLE repair_requests (request_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customer_id INTEGER NOT NULL, " +
                "service_id INTEGER NOT NULL," +
                "technician_id INTEGER," +
                "issue_description TEXT," +
                "issue_photo_path TEXT," +
                "status VARCHAR(20) DEFAULT 'Pending'," +
                "created_at TEXT," +
                "FOREIGN KEY (customer_id) REFERENCES customers(customer_id)," +
                "FOREIGN KEY (service_id) REFERENCES services(service_id)," +
                "FOREIGN KEY (technician_id) REFERENCES technicians(technician_id))";
        db.execSQL(sql);

        sql = "CREATE TABLE booking (booking_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "request_id INTEGER NOT NULL," +
                "service_method TEXT NOT NULL," +
                "pickup_location TEXT NOT NULL," +
                "schedule_date_time TEXT NOT NULL," +
                "created_at TEXT," +
                "FOREIGN KEY (request_id) REFERENCES repair_requests(request_id))";
        db.execSQL(sql);

        sql = "CREATE TABLE notifications(notification_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "customer_id INTEGER NOT NULL," +
                "request_id INTEGER NOT NULL," +
                "message TEXT NOT NULL," +
                "is_read INTEGER DEFAULT 0," +
                "created_at TEXT," +
                "FOREIGN KEY (customer_id) REFERENCES customers(customer_id)," +
                "FOREIGN KEY (request_id) REFERENCES repair_requests(request_id))";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS services");
        db.execSQL("DROP TABLE IF EXISTS devices");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS customers");
        db.execSQL("DROP TABLE IF EXISTS technicians");
        db.execSQL("DROP TABLE IF EXISTS repair_requests");
        db.execSQL("DROP TABLE IF EXISTS customer_saved_devices");
        db.execSQL("DROP TABLE IF EXISTS booking");
        onCreate(db);
    }

    //Devices methods
    private void insertDevice(SQLiteDatabase db, String name, String brand, String type, int imageResId, int serviceIconResID) {

        ContentValues values = new ContentValues();

        values.put("device_name", name);
        values.put("brand", brand);
        values.put("device_type", type);
        values.put("image_res_id", imageResId);
        values.put("service_icon_res_id", serviceIconResID);

        db.insert("devices", null, values);
    }

    public void insertDefaultDevices() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM devices", null);
        if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
            cursor.close();
            return; // already inserted
        }
        cursor.close();

        // Phones
        insertDevice(db, "Galaxy S23", "Samsung", "Phone",
                R.drawable.ic_phone, R.drawable.ic_phone_repair);

        insertDevice(db, "iPhone 14", "Apple", "Phone",
                R.drawable.ic_phone, R.drawable.ic_phone_repair);

        insertDevice(db, "Redmi 14", "Redmi", "Phone",
                R.drawable.ic_phone, R.drawable.ic_phone_repair);

        // Laptops
        insertDevice(db, "MacBook Pro 14", "Apple", "Laptop",
                R.drawable.ic_laptop, R.drawable.ic_laptop_repair);

        insertDevice(db, "HP OmniBook", "HP", "Laptop",
                R.drawable.ic_laptop, R.drawable.ic_laptop_repair);

        // Washing Machines
        insertDevice(db, "BOSCH Front Loader", "BOSCH", "Washing Machine",
                R.drawable.ic_washing_machine, R.drawable.ic_washing_machine_repair);

        insertDevice(db, "Whirlpool Washer Dryer", "Whirlpool", "Washing Machine",
                R.drawable.ic_washing_machine, R.drawable.ic_washing_machine_repair);

        // Air Conditioners
        insertDevice(db, "Air Curtain", "Abans", "AC",
                R.drawable.ic_ac, R.drawable.ic_ac_repair);

        insertDevice(db, "LG Dual Inverter", "LG", "AC",
                R.drawable.ic_ac, R.drawable.ic_ac_repair);

        // TVs
        insertDevice(db, "Singer 32 HD TV", "Singer", "TV",
                R.drawable.ic_tv, R.drawable.ic_tv_repair);

        insertDevice(db, "NanoCell TV", "LG", "TV",
                R.drawable.ic_tv, R.drawable.ic_tv_repair);

        // Computers
        insertDevice(db, "Dell VOSTRO", "Dell", "Computer",
                R.drawable.ic_computer, R.drawable.ic_computer_repair);

        insertDevice(db, "HP 8200 Elite", "HP", "Computer",
                R.drawable.ic_computer, R.drawable.ic_computer_repair);

        // Fridges
        insertDevice(db, "Samsung 253L Digital Inverter", "Samsung", "Fridge",
                R.drawable.ic_fridge, R.drawable.ic_fridge_repair);

        insertDevice(db, "Abans 190L", "Abans", "Fridge",
                R.drawable.ic_fridge, R.drawable.ic_fridge_repair);
    }

    public List<SavedDevice> getSavedDevicesForCustomer(int customerId) {
        List<SavedDevice> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query =
                "SELECT d.device_id, d.device_name, d.brand, d.device_type, cs.is_active " +
                        "FROM customer_saved_devices cs " +
                        "INNER JOIN devices d ON cs.device_id = d.device_id " +
                        "WHERE cs.customer_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(customerId)});

        if (cursor.moveToFirst()) {
            do {
                list.add(new SavedDevice(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) == 1
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public List<Device> getDevicesByType(String type){
        List<Device> deviceList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM devices WHERE device_type=?", new String[]{ type });

        if(cursor.moveToFirst()) {
            do{
                Device device = new Device();

                device.setDeviceId(cursor.getInt(cursor.getColumnIndexOrThrow("device_id")));
                device.setDeviceName(cursor.getString(cursor.getColumnIndexOrThrow("device_name")));
                device.setBrand(cursor.getString(cursor.getColumnIndexOrThrow("brand")));
                device.setDeviceType(cursor.getString(cursor.getColumnIndexOrThrow("device_type")));
                device.setImageResId(cursor.getInt(cursor.getColumnIndexOrThrow("image_res_id")));

                deviceList.add(device);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return deviceList;
    }

    public void addSavedDevices(int customerID, int deviceID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("customer_id", customerID);
        cv.put("device_id",deviceID);
        cv.put("is_active",1);
        db.insert("customer_saved_devices",null, cv);
        db.close();
    }

    public boolean isDeviceAlreadySaved(int customerId, int deviceId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT 1 FROM customer_saved_devices WHERE customer_id=? AND device_id=?",
                new String[]{ String.valueOf(customerId), String.valueOf(deviceId) }
        );

        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public void removeSavedDevice(int customerId, int deviceId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                "customer_saved_devices",
                "customer_id=? AND device_id=?",
                new String[]{String.valueOf(customerId), String.valueOf(deviceId)}
        );
        db.close();
    }

    //service methods
    public List<Service> getServicesByDeviceType(String deviceType) {
        List<Service> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query =
                "SELECT s.* " +
                        "FROM services s " +
                        "INNER JOIN devices d ON s.device_id = d.device_id " +
                        "WHERE d.device_type = ?";

        Cursor cursor = db.rawQuery(query, new String[]{ deviceType });

        if (cursor.moveToFirst()) {
            do {
                Service service = new Service();
                service.setServiceId(cursor.getInt(cursor.getColumnIndexOrThrow("service_id")));
                service.setServiceName(cursor.getString(cursor.getColumnIndexOrThrow("service_name")));
                service.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                service.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("price")));
                service.setDeviceId(cursor.getString(cursor.getColumnIndexOrThrow("device_id")));
                service.setEstimatedDuration(cursor.getInt(cursor.getColumnIndexOrThrow("estimated_duration")));
                service.setImageResId(cursor.getInt(cursor.getColumnIndexOrThrow("image_res_id")));

                list.add(service);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public void insertDefaultServices() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM services", null);
        if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
            cursor.close();
            return;
        }
        cursor.close();

        int phoneId = getDeviceIdByType(db, "Phone");
        int laptopId = getDeviceIdByType(db, "Laptop");
        int computerId = getDeviceIdByType(db, "Computer");
        int acId = getDeviceIdByType(db, "AC");
        int tvId = getDeviceIdByType(db, "TV");
        int washingId = getDeviceIdByType(db, "Washing Machine");
        int fridgeId = getDeviceIdByType(db, "Fridge");

        // Phone
        insertService(db, "Screen Replacement", "Replace cracked or damaged screen",4500, phoneId,3);
        insertService(db, "Battery Replacement","Fix battery draining or charging issues", 2500, phoneId, 2);
        insertService(db, "Charging Port Repair","Fix loose or damaged charging port to restore proper charging", 3000, phoneId,2);

        // Laptop
        insertService(db, "Keyboard Replacement", "Replace faulty or broken laptop keyboard for smooth typing",4000, laptopId,1);
        insertService(db, "Laptop Screen Repair","Repair or replace cracked or malfunctioning laptop display", 12000, laptopId,4);
        insertService(db, "OS Installation","Install or reinstall operating system with drivers and updates", 3500, laptopId,2);

        // Computer
        insertService(db, "RAM Upgrade","Upgrade system memory for faster performance and multitasking", 5000, computerId,2);
        insertService(db, "Hard Disk Replacement","Replace faulty hard drive and reinstall system if required", 8000, computerId,3);

        // AC
        insertService(db, "AC Gas Refill","Refill refrigerant gas to restore cooling efficiency", 6000, acId,2);
        insertService(db, "AC Cleaning", "Clean filters, coils, and ducts for better airflow and hygiene",3000, acId,2);

        // TV
        insertService(db, "Display Repair","Fix or replace damaged TV screen for clear visuals", 15000, tvId,4);
        insertService(db, "Sound Issue Fix", "Repair speaker or audio board to resolve sound problems",5000, tvId,2);

        // Washing Machine
        insertService(db, "Drum Repair","Repair or replace damaged drum to ensure smooth washing cycles", 7000, washingId,3);
        insertService(db, "Water Leakage Fix","Identify and fix leaks from hoses, valves, or seals", 4500, washingId,2);

        // Fridge
        insertService(db, "Cooling Issue Repair","Fix compressor, thermostat, or coils to restore cooling", 8000, fridgeId,3);
        insertService(db, "Gas Refill", "Refill refrigerant gas to maintain proper cooling performance",6000, fridgeId,2);
    }



    private void insertService(SQLiteDatabase db, String name,
                               String desc, double price, int deviceId, int duration) {

        ContentValues cv = new ContentValues();
        cv.put("service_name", name);
        cv.put("description", desc);
        cv.put("price", price);
        cv.put("device_id", deviceId);
        cv.put("estimated_duration", duration);
        cv.put("image_res_id", 0);

        db.insert("services", null, cv);
    }

    public List<Device> getServiceCategories() {
        List<Device> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query =
                "SELECT DISTINCT device_type, service_icon_res_id " +
                        "FROM devices d " +
                        "INNER JOIN services s ON d.device_id = s.device_id";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Device device = new Device();
                device.setDeviceType(cursor.getString(0));
                device.setServiceIconResId(cursor.getInt(1));
                list.add(device);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
    private int getDeviceIdByType(SQLiteDatabase db, String deviceType) {
        Cursor cursor = db.rawQuery(
                "SELECT device_id FROM devices WHERE device_type=? LIMIT 1",
                new String[]{deviceType}
        );

        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public Service getServiceById(int serviceId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM services WHERE service_id=?",
                new String[]{String.valueOf(serviceId)}
        );

        if (cursor.moveToFirst()) {
            Service s = new Service();
            s.setServiceId(serviceId);
            s.setServiceName(cursor.getString(cursor.getColumnIndexOrThrow("service_name")));
            s.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("price")));
            cursor.close();
            return s;
        }

        cursor.close();
        return null;
    }
    public List<Service> searchServices(String keyword) {
        List<Service> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM services WHERE service_name LIKE ?",
                new String[]{"%" + keyword + "%"}
        );

        if (c.moveToFirst()) {
            do {
                Service s = new Service();
                s.setServiceId(c.getInt(c.getColumnIndexOrThrow("service_id")));
                s.setServiceName(c.getString(c.getColumnIndexOrThrow("service_name")));
                s.setDescription(c.getString(c.getColumnIndexOrThrow("description")));
                s.setPrice(c.getDouble(c.getColumnIndexOrThrow("price")));
                s.setDeviceId(c.getString(c.getColumnIndexOrThrow("device_id")));
                list.add(s);
            } while (c.moveToNext());
        }

        c.close();
        return list;
    }

    //Booking methods
    public void insertBooking(long requestId, String method,
                              String location, String dateTime) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("request_id", requestId);
        cv.put("service_method", method);
        cv.put("pickup_location", location);
        cv.put("schedule_date_time", dateTime);
        cv.put("created_at",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        db.insert("booking", null, cv);
    }
    public List<Booking> getBookingHistory(int customerId) {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query =
                "SELECT b.booking_id, b.request_id, b.service_method, " +
                        "b.pickup_location, b.schedule_date_time, b.created_at, " +
                        "s.service_name " +
                        "FROM booking b " +
                        "INNER JOIN repair_requests r ON b.request_id = r.request_id " +
                        "INNER JOIN services s ON r.service_id = s.service_id " +
                        "WHERE r.customer_id = ? " +
                        "ORDER BY b.created_at DESC";

        Cursor c = db.rawQuery(query,
                new String[]{String.valueOf(customerId)});

        if (c.moveToFirst()) {
            do {
                list.add(new Booking(
                        c.getLong(0),
                        c.getLong(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6)
                ));
            } while (c.moveToNext());
        }

        c.close();
        return list;
    }



    //repair request methods
    public long insertRepairRequest(int customerId, int serviceId, String issueDesc, String photoPath) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("customer_id", customerId);
        cv.put("service_id", serviceId);
        cv.put("issue_description", issueDesc);
        cv.put("issue_photo_path", photoPath);
        cv.put("status", "PENDING");
        cv.put("created_at", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()));

        return db.insert("repair_requests", null, cv);
    }
    public void updateRepairStatus(long requestId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("status", status);

        db.update(
                "repair_requests",
                cv,
                "request_id=?",
                new String[]{String.valueOf(requestId)}
        );
    }

    public int getCustomerIdByRequestId(int requestId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT customer_id FROM repair_requests WHERE request_id=?",
                new String[]{String.valueOf(requestId)}
        );

        int customerId = -1;
        if (c.moveToFirst()) {
            customerId = c.getInt(0);
        }
        c.close();
        return customerId;
    }



    //notification methods
    public void insertNotification(int customerId, Integer requestId, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("customer_id", customerId);
        cv.put("request_id", requestId);
        cv.put("message", message);
        cv.put("is_read", 0);
        cv.put("created_at",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        db.insert("notifications", null, cv);
    }
    public List<Notification> getNotifications(int customerId) {
        List<Notification> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM notifications WHERE customer_id=? ORDER BY created_at DESC",
                new String[]{String.valueOf(customerId)}
        );

        if (cursor.moveToFirst()) {
            do {
                Notification n = new Notification();
                n.setNotificationId(cursor.getInt(0));
                n.setCustomerId(cursor.getInt(1));
                n.setRequestId(cursor.getInt(2));
                n.setMessage(cursor.getString(3));
                n.setRead(cursor.getInt(4) == 1);
                n.setCreatedAt(cursor.getString(5));
                list.add(n);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
    public void markNotificationAsRead(int notificationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("is_read", 1);

        db.update(
                "notifications",
                cv,
                "notification_id=?",
                new String[]{String.valueOf(notificationId)}
        );
    }
    public int getUnreadNotificationCount(int customerId) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT COUNT(*) FROM notifications WHERE customer_id = ? AND is_read = 0",
                new String[]{String.valueOf(customerId)}
        );

        int count = 0;
        if (c.moveToFirst()) {
            count = c.getInt(0);
        }
        c.close();
        return count;
    }



    // customer edit profile methods
    public UserProfile getCustomerProfile(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql =
                "SELECT u.full_name, u.email, c.phone, c.address " +
                        "FROM users u JOIN customers c ON u.user_id = c.user_id " +
                        "WHERE c.customer_id = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(customerId)});

        if (cursor.moveToFirst()) {
            UserProfile profile = new UserProfile(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            cursor.close();
            return profile;
        }

        cursor.close();
        return null;
    }
    public boolean updateUserInfo(int customerId, String fullName, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql =
                "UPDATE users SET full_name = ?, email = ? " +
                        "WHERE user_id = (SELECT user_id FROM customers WHERE customer_id = ?)";

        db.execSQL(sql, new Object[]{fullName, email, customerId});
        return true;
    }
    public boolean updateCustomerInfo(int customerId, String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql =
                "UPDATE customers SET phone = ?, address = ? WHERE customer_id = ?";

        db.execSQL(sql, new Object[]{phone, address, customerId});
        return true;
    }
    public void updatePassword(int customerId, String passwordHash) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql =
                "UPDATE users SET password_hash = ? " +
                        "WHERE user_id = (SELECT user_id FROM customers WHERE customer_id = ?)";

        db.execSQL(sql, new Object[]{passwordHash, customerId});
    }


// customer login + signup methods
public boolean isEmailExists(String email) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(
            "SELECT user_id FROM users WHERE email = ?",
            new String[]{email}
    );
    boolean exists = c.moveToFirst();
    c.close();
    return exists;
}
    public boolean registerCustomer(
            String email,
            String fullName,
            String password,
            String phone,
            String address
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues userValues = new ContentValues();
            userValues.put("email", email);
            userValues.put("full_name", fullName);

            String hashed = PasswordUtil.hashPassword(password);
            userValues.put("password_hash", hashed);

            userValues.put("role", "customer");

            long userId = db.insert("users", null, userValues);
            if (userId == -1) return false;

            ContentValues customerValues = new ContentValues();
            customerValues.put("user_id", userId);
            customerValues.put("phone", phone);
            customerValues.put("address", address);

            long custId = db.insert("customers", null, customerValues);
            if (custId == -1) return false;

            db.setTransactionSuccessful();
            return true;
        } finally {
            db.endTransaction();
        }
    }
    public Cursor login(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String hashed = PasswordUtil.hashPassword(password);

        return db.rawQuery(
                "SELECT user_id, role, full_name FROM users WHERE email = ? AND password_hash = ?",
                new String[]{email, hashed}
        );
    }
    public int getCustomerIdByUserId(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT customer_id FROM customers WHERE user_id = ?",
                new String[]{String.valueOf(userId)}
        );

        int id = -1;
        if (c.moveToFirst()) {
            id = c.getInt(0);
        }
        c.close();
        return id;
    }


    //technician methods
    public boolean hasTechnicians() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT technician_id FROM technicians LIMIT 1", null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }
    private void insertTechnician(
            SQLiteDatabase db,
            String email,
            String fullName,
            String passwordHash,
            String specialization
    ) {
        ContentValues userValues = new ContentValues();
        userValues.put("email", email);
        userValues.put("full_name", fullName);
        userValues.put("password_hash", passwordHash);
        userValues.put("role", "technician");

        long userId = db.insert("users", null, userValues);
        if (userId == -1) return;

        ContentValues techValues = new ContentValues();
        techValues.put("user_id", userId);
        techValues.put("specialization", specialization);
        techValues.put("availability_status", "AVAILABLE");

        db.insert("technicians", null, techValues);
    }

    public void insertDefaultTechnicians() {
        SQLiteDatabase db = this.getWritableDatabase();

        if (hasTechnicians()) return;

        db.beginTransaction();
        try {

            insertTechnician(db,
                    "alex@techcare.com",
                    "Alex Perera",
                    PasswordUtil.hashPassword("tech123"),
                    "Phone Repair");

            insertTechnician(db,
                    "nimal@techcare.com",
                    "Nimal Silva",
                    PasswordUtil.hashPassword("tech123"),
                    "Laptop Repair");

            insertTechnician(db,
                    "sahan@techcare.com",
                    "Sahan Fernando",
                    PasswordUtil.hashPassword("tech123"),
                    "Tablet Repair");

            insertTechnician(db,
                    "kasun@techcare.com",
                    "Kasun Jayasinghe",
                    PasswordUtil.hashPassword("tech123"),
                    "Software Issues");

            insertTechnician(db,
                    "Ishara@techcare.com",
                    "ishara Gunawardena",
                    PasswordUtil.hashPassword("tech123"),
                    "Hardware Diagnostics");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    public Technician getTechnicianByUserId(long userId) {

        SQLiteDatabase db = this.getReadableDatabase();

        String sql =
                "SELECT t.technician_id, u.full_name, u.email, " +
                        "t.specialization, t.availability_status " +
                        "FROM technicians t " +
                        "JOIN users u ON t.user_id = u.user_id " +
                        "WHERE u.user_id = ?";

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(userId)});

        Technician technician = null;

        if (c.moveToFirst()) {

            technician = new Technician(
                    (int) userId,
                    c.getInt(c.getColumnIndexOrThrow("technician_id")),
                    c.getString(c.getColumnIndexOrThrow("full_name")),
                    c.getString(c.getColumnIndexOrThrow("email")),
                    null, // password not needed here
                    c.getString(c.getColumnIndexOrThrow("specialization")),
                    c.getString(c.getColumnIndexOrThrow("availability_status"))
            );
        }

        c.close();
        return technician;
    }
    public List<RepairRequestItem> getPendingRepairRequestsForTechnician(int technicianId) {

        List<RepairRequestItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql =
                "SELECT r.request_id, r.customer_id, u.full_name, " +
                        "s.service_name, r.created_at, IFNULL(b.service_method, 'N/A') " +
                        "FROM repair_requests r " +
                        "JOIN customers c ON r.customer_id = c.customer_id " +
                        "JOIN users u ON c.user_id = u.user_id " +
                        "JOIN services s ON r.service_id = s.service_id " +
                        "LEFT JOIN booking b ON b.request_id = r.request_id " +
                        "WHERE r.status IN ('PENDING', 'BOOKED') " +
                        "AND r.technician_id IS NULL";

        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {

            int requestId = c.getInt(0);
            int customerId = c.getInt(1);
            String customerName = c.getString(2);
            String serviceName = c.getString(3);
            String date = c.getString(4);
            String serviceMethod = c.getString(5);

            list.add(new RepairRequestItem(
                    requestId,
                    customerId,
                    customerName,
                    serviceName,
                    serviceMethod,
                    date
            ));
        }

        c.close();
        return list;
    }

    public void acceptRepairRequest(int requestId, int technicianId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql =
                "UPDATE repair_requests " +
                        "SET status = 'ACCEPTED', technician_id = ? " +
                        "WHERE request_id = ?";

        db.execSQL(sql, new Object[]{technicianId, requestId});
    }
    public void rejectRepairRequest(int requestId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql =
                "UPDATE repair_requests " +
                        "SET status = 'REJECTED' " +
                        "WHERE request_id = ?";

        db.execSQL(sql, new Object[]{requestId});
    }
    public int getTechnicianIdByUserId(long userId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT technician_id FROM technicians WHERE user_id = ?",
                new String[]{String.valueOf(userId)}
        );

        int id = -1;
        if (c.moveToFirst()) {
            id = c.getInt(0);
        }

        c.close();
        return id;
    }
    public void updateRepairStatus(int requestId, String status) {

        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("STATUS_FLOW",
                "DB update STARTED → requestId=" + requestId + ", newStatus=" + status);

        ContentValues cv = new ContentValues();
        cv.put("status", status);

        int rows = db.update(
                "repair_requests",
                cv,
                "request_id = ?",
                new String[]{String.valueOf(requestId)}
        );

        Log.d("STATUS_FLOW",
                "DB update FINISHED → rowsAffected=" + rows);
    }

    public List<AcceptedRepairItem> getAcceptedRequestsForTechnician(int technicianId) {

        List<AcceptedRepairItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql =
                "SELECT r.request_id, u.full_name, s.service_name, r.created_at, r.status " +
                        "FROM repair_requests r " +
                        "JOIN customers c ON r.customer_id = c.customer_id " +
                        "JOIN users u ON c.user_id = u.user_id " +
                        "JOIN services s ON r.service_id = s.service_id " +
                        "WHERE r.status IN ('ACCEPTED', 'RECEIVED', 'UNDER_REPAIR') " +
                        "AND r.technician_id = ?";

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(technicianId)});

        while (c.moveToNext()) {
            list.add(new AcceptedRepairItem(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4)
            ));
        }

        c.close();
        return list;
    }

    public List<HistoryRepairment> getRepairHistoryForTechnician(int technicianId) {

        List<HistoryRepairment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql =
                "SELECT r.request_id, u.full_name, s.service_name, r.created_at " +
                        "FROM repair_requests r " +
                        "JOIN customers c ON r.customer_id = c.customer_id " +
                        "JOIN users u ON c.user_id = u.user_id " +
                        "JOIN services s ON r.service_id = s.service_id " +
                        "WHERE r.status = 'READY' AND r.technician_id = ? " +
                        "ORDER BY r.created_at DESC";

        Cursor c = db.rawQuery(sql,
                new String[]{String.valueOf(technicianId)});

        while (c.moveToNext()) {

            int requestId = c.getInt(0);
            String customerName = c.getString(1);
            String serviceName = c.getString(2);
            String date = c.getString(3);

            list.add(new HistoryRepairment(
                    requestId,
                    customerName,
                    serviceName,
                    date
            ));
        }

        c.close();
        return list;
    }

    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM booking", null);
    }
    public boolean isBookingTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM booking", null);
        boolean isEmpty = (cursor.getCount() == 0);
        cursor.close();
        return isEmpty;

    }
    public Cursor getRepairRequests() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT request_id, status FROM repair_requests", null);
    }
    public String getRepairStatusById(long requestId) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT status FROM repair_requests WHERE request_id = ?",
                new String[]{String.valueOf(requestId)}
        );

        String status = "NOT_FOUND";

        if (c.moveToFirst()) {
            status = c.getString(0);
        }

        c.close();
        return status;
    }
}
