-- daily rental report for the entire company: shows each branch and each vehicle type
SELECT r.fromDateTime AS "Date",
       v.location AS "Location", 
       SUM (COUNT (v.vid)) OVER (PARTITION BY v.location ORDER BY v.location ASC) AS "Total new rentals at branch", 
       vt.vtname AS "Vehicle type", 
       COUNT (v.vid) AS "Total new rentals"
FROM Vehicle v, Rentals r, VehicleTypes vt
WHERE (r.vid = v.vid) AND (r.fromDateTime < TO_TIMESTAMP('2019-10-14', 'YYYY-MM-DD'))
GROUP BY v.location, v.vtname
ORDER BY r.fromDateTime ASC;

-- daily rental report for the specific branch
SELECT r.fromDateTime AS "Date",
       v.location AS "Location", 
       SUM (COUNT (v.vid)) OVER (PARTITION BY v.location ORDER BY v.location ASC) AS "Total new rentals at branch", 
       vt.vtname AS "Vehicle type", 
       COUNT (v.vid) AS "Total new rentals"
FROM Vehicle v, Rentals r, VehicleTypes vt
WHERE (v.location = 'Mia Street') AND (r.vid = v.vid) AND (r.fromDateTime < TO_TIMESTAMP('2019-10-14', 'YYYY-MM-DD'))
GROUP BY v.location, vt.vtname
ORDER BY r.fromDateTime ASC;

-- daily return report for the entire company
SELECT rt.rdate AS "Return date",
       v.location AS "Location", 
       SUM (COUNT (v.vid)) OVER (PARTITION BY v.location ORDER BY v.location ASC) AS "Total new returns at branch", 
       vt.vtname AS "Vehicle type", 
       COUNT (v.vid) AS "Total new returns"
       SUM (rt.value) AS "Total revenue",
       SUM (SUM (rt.value)) OVER (PARTITION BY v.location) AS "Total revenue at branch"
FROM Vehicle v, Rentals r, [Returns] rt, VehicleTypes vt
WHERE (rt.rid = r.rid) AND (r.vid = v.vid) AND (rt.rdate = TO_TIMESTAMP('2019-10-18', 'YYYY-MM-DD'))
GROUP BY v.location, vt.vtname
ORDER BY v.location ASC;

-- daily return report for the specific branch
SELECT rt.rdate AS "Return date",
       v.location AS "Location", 
       SUM (COUNT (v.vid)) OVER (PARTITION BY v.location ORDER BY v.location ASC) AS "Total new returns at branch", 
       vt.vtname AS "Vehicle type", 
       COUNT (v.vid) AS "Total new returns"
       SUM (rt.value) AS "Total revenue",
       SUM (SUM (rt.value)) OVER (PARTITION BY v.location) AS "Total revenue at branch"
FROM Vehicle v, Rentals r, [Returns] rt, VehicleTypes vt
WHERE (v.location = 'Scott Road') AND (rt.rid = r.rid) AND (r.vid = v.vid) AND (rt.rdate = TO_TIMESTAMP('2019-10-18', 'YYYY-MM-DD'))
GROUP BY v.vlocation, v.vtname
ORDER BY v.location ASC;
