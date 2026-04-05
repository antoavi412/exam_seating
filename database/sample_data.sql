-- ===============================================
-- Sample Data for Exam Seating Arrangement System
-- ===============================================

USE exam_seating_db;

-- ===============================================
-- Insert Departments
-- ===============================================
INSERT INTO departments (code, name) VALUES
('CSE', 'Computer Science and Engineering'),
('ECE', 'Electronics and Communication Engineering'),
('ME', 'Mechanical Engineering'),
('CE', 'Civil Engineering'),
('EEE', 'Electrical and Electronics Engineering'),
('IT', 'Information Technology'),
('CHEM', 'Chemical Engineering'),
('BT', 'Biotechnology');

-- ===============================================
-- Insert Students (200 students across departments)
-- ===============================================
INSERT INTO students (roll_number, name, department_id, year, email) VALUES
-- CSE Department (40 students)
('CSE2021001', 'Aarav Sharma', 1, 3, 'aarav.sharma@university.edu'),
('CSE2021002', 'Vivaan Gupta', 1, 3, 'vivaan.gupta@university.edu'),
('CSE2021003', 'Aditya Kumar', 1, 3, 'aditya.kumar@university.edu'),
('CSE2021004', 'Sai Reddy', 1, 3, 'sai.reddy@university.edu'),
('CSE2021005', 'Arjun Patel', 1, 3, 'arjun.patel@university.edu'),
('CSE2021006', 'Reyansh Singh', 1, 3, 'reyansh.singh@university.edu'),
('CSE2021007', 'Ayush Verma', 1, 3, 'ayush.verma@university.edu'),
('CSE2021008', 'Krishna Nair', 1, 3, 'krishna.nair@university.edu'),
('CSE2021009', 'Ishaan Joshi', 1, 3, 'ishaan.joshi@university.edu'),
('CSE2021010', 'Advait Desai', 1, 3, 'advait.desai@university.edu'),
('CSE2021011', 'Ananya Iyer', 1, 3, 'ananya.iyer@university.edu'),
('CSE2021012', 'Diya Menon', 1, 3, 'diya.menon@university.edu'),
('CSE2021013', 'Saanvi Krishnan', 1, 3, 'saanvi.krishnan@university.edu'),
('CSE2021014', 'Aadhya Rao', 1, 3, 'aadhya.rao@university.edu'),
('CSE2021015', 'Kiara Kapoor', 1, 3, 'kiara.kapoor@university.edu'),
('CSE2021016', 'Navya Shah', 1, 3, 'navya.shah@university.edu'),
('CSE2021017', 'Pari Agarwal', 1, 3, 'pari.agarwal@university.edu'),
('CSE2021018', 'Myra Bansal', 1, 3, 'myra.bansal@university.edu'),
('CSE2021019', 'Sara Malhotra', 1, 3, 'sara.malhotra@university.edu'),
('CSE2021020', 'Aarohi Saxena', 1, 3, 'aarohi.saxena@university.edu'),
('CSE2022001', 'Aryan Mehta', 1, 2, 'aryan.mehta@university.edu'),
('CSE2022002', 'Shaurya Jain', 1, 2, 'shaurya.jain@university.edu'),
('CSE2022003', 'Dhruv Chopra', 1, 2, 'dhruv.chopra@university.edu'),
('CSE2022004', 'Vihaan Sinha', 1, 2, 'vihaan.sinha@university.edu'),
('CSE2022005', 'Atharv Bhatt', 1, 2, 'atharv.bhatt@university.edu'),
('CSE2022006', 'Arnav Pandey', 1, 2, 'arnav.pandey@university.edu'),
('CSE2022007', 'Kabir Tiwari', 1, 2, 'kabir.tiwari@university.edu'),
('CSE2022008', 'Rudra Dubey', 1, 2, 'rudra.dubey@university.edu'),
('CSE2022009', 'Pranav Mishra', 1, 2, 'pranav.mishra@university.edu'),
('CSE2022010', 'Shivansh Yadav', 1, 2, 'shivansh.yadav@university.edu'),
('CSE2022011', 'Anika Chatterjee', 1, 2, 'anika.chatterjee@university.edu'),
('CSE2022012', 'Ira Mukherjee', 1, 2, 'ira.mukherjee@university.edu'),
('CSE2022013', 'Tara Bose', 1, 2, 'tara.bose@university.edu'),
('CSE2022014', 'Zara Roy', 1, 2, 'zara.roy@university.edu'),
('CSE2022015', 'Inaya Das', 1, 2, 'inaya.das@university.edu'),
('CSE2022016', 'Riya Sen', 1, 2, 'riya.sen@university.edu'),
('CSE2022017', 'Avni Ghosh', 1, 2, 'avni.ghosh@university.edu'),
('CSE2022018', 'Prisha Dutta', 1, 2, 'prisha.dutta@university.edu'),
('CSE2022019', 'Shanaya Sarkar', 1, 2, 'shanaya.sarkar@university.edu'),
('CSE2022020', 'Mira Thakur', 1, 2, 'mira.thakur@university.edu'),

-- ECE Department (35 students)
('ECE2021001', 'Aayush Bhatnagar', 2, 3, 'aayush.bhatnagar@university.edu'),
('ECE2021002', 'Ved Raghavan', 2, 3, 'ved.raghavan@university.edu'),
('ECE2021003', 'Laksh Venkat', 2, 3, 'laksh.venkat@university.edu'),
('ECE2021004', 'Kian Subramanian', 2, 3, 'kian.subramanian@university.edu'),
('ECE2021005', 'Ojas Ramesh', 2, 3, 'ojas.ramesh@university.edu'),
('ECE2021006', 'Aarav Balaji', 2, 3, 'aarav.balaji@university.edu'),
('ECE2021007', 'Darsh Narayan', 2, 3, 'darsh.narayan@university.edu'),
('ECE2021008', 'Yash Raman', 2, 3, 'yash.raman@university.edu'),
('ECE2021009', 'Veer Mohan', 2, 3, 'veer.mohan@university.edu'),
('ECE2021010', 'Kartik Gopal', 2, 3, 'kartik.gopal@university.edu'),
('ECE2021011', 'Aaradhya Pillai', 2, 3, 'aaradhya.pillai@university.edu'),
('ECE2021012', 'Ahana Varma', 2, 3, 'ahana.varma@university.edu'),
('ECE2021013', 'Aria Shetty', 2, 3, 'aria.shetty@university.edu'),
('ECE2021014', 'Kavya Hegde', 2, 3, 'kavya.hegde@university.edu'),
('ECE2021015', 'Mahi Kamath', 2, 3, 'mahi.kamath@university.edu'),
('ECE2022001', 'Nirvaan Kumar', 2, 2, 'nirvaan.kumar@university.edu'),
('ECE2022002', 'Raghav Shenoy', 2, 2, 'raghav.shenoy@university.edu'),
('ECE2022003', 'Rishaan Rao', 2, 2, 'rishaan.rao@university.edu'),
('ECE2022004', 'Tanish Pai', 2, 2, 'tanish.pai@university.edu'),
('ECE2022005', 'Advay Nayak', 2, 2, 'advay.nayak@university.edu'),
('ECE2022006', 'Aarush Kulkarni', 2, 2, 'aarush.kulkarni@university.edu'),
('ECE2022007', 'Vivaan Joshi', 2, 2, 'vivaan.joshi@university.edu'),
('ECE2022008', 'Shlok Patil', 2, 2, 'shlok.patil@university.edu'),
('ECE2022009', 'Parth Deshpande', 2, 2, 'parth.deshpande@university.edu'),
('ECE2022010', 'Arhaan Shinde', 2, 2, 'arhaan.shinde@university.edu'),
('ECE2022011', 'Pihu Deshmukh', 2, 2, 'pihu.deshmukh@university.edu'),
('ECE2022012', 'Anvi Kale', 2, 2, 'anvi.kale@university.edu'),
('ECE2022013', 'Aadhira Tambe', 2, 2, 'aadhira.tambe@university.edu'),
('ECE2022014', 'Nitya Jadhav', 2, 2, 'nitya.jadhav@university.edu'),
('ECE2022015', 'Mahika Sawant', 2, 2, 'mahika.sawant@university.edu'),
('ECE2022016', 'Krisha More', 2, 2, 'krisha.more@university.edu'),
('ECE2022017', 'Vanya Bhosale', 2, 2, 'vanya.bhosale@university.edu'),
('ECE2022018', 'Janvi Salvi', 2, 2, 'janvi.salvi@university.edu'),
('ECE2022019', 'Hiya Wagh', 2, 2, 'hiya.wagh@university.edu'),
('ECE2022020', 'Reet Pawar', 2, 2, 'reet.pawar@university.edu'),

-- ME Department (30 students)
('ME2021001', 'Kabir Malhotra', 3, 3, 'kabir.malhotra@university.edu'),
('ME2021002', 'Ayaan Khanna', 3, 3, 'ayaan.khanna@university.edu'),
('ME2021003', 'Zayan Batra', 3, 3, 'zayan.batra@university.edu'),
('ME2021004', 'Reyansh Arora', 3, 3, 'reyansh.arora@university.edu'),
('ME2021005', 'Shivansh Grover', 3, 3, 'shivansh.grover@university.edu'),
('ME2021006', 'Viaan Talwar', 3, 3, 'viaan.talwar@university.edu'),
('ME2021007', 'Aarav Kohli', 3, 3, 'aarav.kohli@university.edu'),
('ME2021008', 'Ansh Sethi', 3, 3, 'ansh.sethi@university.edu'),
('ME2021009', 'Hriday Bajaj', 3, 3, 'hriday.bajaj@university.edu'),
('ME2021010', 'Shaurya Dhawan', 3, 3, 'shaurya.dhawan@university.edu'),
('ME2021011', 'Myra Suri', 3, 3, 'myra.suri@university.edu'),
('ME2021012', 'Anika Kapur', 3, 3, 'anika.kapur@university.edu'),
('ME2021013', 'Inaya Vohra', 3, 3, 'inaya.vohra@university.edu'),
('ME2021014', 'Aadhya Ahuja', 3, 3, 'aadhya.ahuja@university.edu'),
('ME2021015', 'Saanvi Mehra', 3, 3, 'saanvi.mehra@university.edu'),
('ME2022001', 'Arjun Anand', 3, 2, 'arjun.anand@university.edu'),
('ME2022002', 'Vihaan Mittal', 3, 2, 'vihaan.mittal@university.edu'),
('ME2022003', 'Aditya Chadha', 3, 2, 'aditya.chadha@university.edu'),
('ME2022004', 'Rudra Oberoi', 3, 2, 'rudra.oberoi@university.edu'),
('ME2022005', 'Ayush Luthra', 3, 2, 'ayush.luthra@university.edu'),
('ME2022006', 'Dhruv Bindra', 3, 2, 'dhruv.bindra@university.edu'),
('ME2022007', 'Atharv Bahl', 3, 2, 'atharv.bahl@university.edu'),
('ME2022008', 'Arnav Chawla', 3, 2, 'arnav.chawla@university.edu'),
('ME2022009', 'Sai Sabharwal', 3, 2, 'sai.sabharwal@university.edu'),
('ME2022010', 'Krishna Tandon', 3, 2, 'krishna.tandon@university.edu'),
('ME2022011', 'Anaya Khurana', 3, 2, 'anaya.khurana@university.edu'),
('ME2022012', 'Pari Randhawa', 3, 2, 'pari.randhawa@university.edu'),
('ME2022013', 'Navya Sodhi', 3, 2, 'navya.sodhi@university.edu'),
('ME2022014', 'Kiara Wadhwa', 3, 2, 'kiara.wadhwa@university.edu'),
('ME2022015', 'Sara Madan', 3, 2, 'sara.madan@university.edu');

-- Continuing with more departments...
INSERT INTO students (roll_number, name, department_id, year, email) VALUES
-- CE Department (25 students)
('CE2021001', 'Advait Sharma', 4, 3, 'advait.sharma@university.edu'),
('CE2021002', 'Kian Patel', 4, 3, 'kian.patel@university.edu'),
('CE2021003', 'Vivaan Reddy', 4, 3, 'vivaan.reddy@university.edu'),
('CE2021004', 'Arhaan Gupta', 4, 3, 'arhaan.gupta@university.edu'),
('CE2021005', 'Darsh Kumar', 4, 3, 'darsh.kumar@university.edu'),
('CE2021006', 'Aarav Singh', 4, 3, 'aarav.singh@university.edu'),
('CE2021007', 'Veer Verma', 4, 3, 'veer.verma@university.edu'),
('CE2021008', 'Yash Nair', 4, 3, 'yash.nair@university.edu'),
('CE2021009', 'Laksh Joshi', 4, 3, 'laksh.joshi@university.edu'),
('CE2021010', 'Kartik Desai', 4, 3, 'kartik.desai@university.edu'),
('CE2021011', 'Diya Iyer', 4, 3, 'diya.iyer@university.edu'),
('CE2021012', 'Ira Menon', 4, 3, 'ira.menon@university.edu'),
('CE2021013', 'Tara Krishnan', 4, 3, 'tara.krishnan@university.edu'),
('CE2022001', 'Nirvaan Rao', 4, 2, 'nirvaan.rao@university.edu'),
('CE2022002', 'Raghav Kapoor', 4, 2, 'raghav.kapoor@university.edu'),
('CE2022003', 'Tanish Shah', 4, 2, 'tanish.shah@university.edu'),
('CE2022004', 'Advay Agarwal', 4, 2, 'advay.agarwal@university.edu'),
('CE2022005', 'Aarush Bansal', 4, 2, 'aarush.bansal@university.edu'),
('CE2022006', 'Shlok Malhotra', 4, 2, 'shlok.malhotra@university.edu'),
('CE2022007', 'Parth Saxena', 4, 2, 'parth.saxena@university.edu'),
('CE2022008', 'Pihu Mehta', 4, 2, 'pihu.mehta@university.edu'),
('CE2022009', 'Anvi Jain', 4, 2, 'anvi.jain@university.edu'),
('CE2022010', 'Aadhira Chopra', 4, 2, 'aadhira.chopra@university.edu'),
('CE2022011', 'Nitya Sinha', 4, 2, 'nitya.sinha@university.edu'),
('CE2022012', 'Mahika Bhatt', 4, 2, 'mahika.bhatt@university.edu'),

-- EEE Department (25 students)
('EEE2021001', 'Ojas Pandey', 5, 3, 'ojas.pandey@university.edu'),
('EEE2021002', 'Ved Tiwari', 5, 3, 'ved.tiwari@university.edu'),
('EEE2021003', 'Aryan Dubey', 5, 3, 'aryan.dubey@university.edu'),
('EEE2021004', 'Shaurya Mishra', 5, 3, 'shaurya.mishra@university.edu'),
('EEE2021005', 'Dhruv Yadav', 5, 3, 'dhruv.yadav@university.edu'),
('EEE2021006', 'Vihaan Chatterjee', 5, 3, 'vihaan.chatterjee@university.edu'),
('EEE2021007', 'Atharv Mukherjee', 5, 3, 'atharv.mukherjee@university.edu'),
('EEE2021008', 'Arnav Bose', 5, 3, 'arnav.bose@university.edu'),
('EEE2021009', 'Kabir Roy', 5, 3, 'kabir.roy@university.edu'),
('EEE2021010', 'Rudra Das', 5, 3, 'rudra.das@university.edu'),
('EEE2021011', 'Zara Sen', 5, 3, 'zara.sen@university.edu'),
('EEE2021012', 'Riya Ghosh', 5, 3, 'riya.ghosh@university.edu'),
('EEE2021013', 'Avni Dutta', 5, 3, 'avni.dutta@university.edu'),
('EEE2022001', 'Pranav Sarkar', 5, 2, 'pranav.sarkar@university.edu'),
('EEE2022002', 'Shivansh Thakur', 5, 2, 'shivansh.thakur@university.edu'),
('EEE2022003', 'Aayush Bhatnagar', 5, 2, 'aayush.bhatnagar2@university.edu'),
('EEE2022004', 'Reyansh Raghavan', 5, 2, 'reyansh.raghavan@university.edu'),
('EEE2022005', 'Ayush Venkat', 5, 2, 'ayush.venkat@university.edu'),
('EEE2022006', 'Krishna Subramanian', 5, 2, 'krishna.subramanian@university.edu'),
('EEE2022007', 'Ishaan Ramesh', 5, 2, 'ishaan.ramesh@university.edu'),
('EEE2022008', 'Anika Balaji', 5, 2, 'anika.balaji@university.edu'),
('EEE2022009', 'Shanaya Narayan', 5, 2, 'shanaya.narayan@university.edu'),
('EEE2022010', 'Mira Raman', 5, 2, 'mira.raman@university.edu'),
('EEE2022011', 'Prisha Mohan', 5, 2, 'prisha.mohan@university.edu'),
('EEE2022012', 'Aarohi Gopal', 5, 2, 'aarohi.gopal@university.edu'),

-- IT Department (25 students)
('IT2021001', 'Advait Pillai', 6, 3, 'advait.pillai@university.edu'),
('IT2021002', 'Kian Varma', 6, 3, 'kian.varma@university.edu'),
('IT2021003', 'Aarav Shetty', 6, 3, 'aarav.shetty@university.edu'),
('IT2021004', 'Darsh Hegde', 6, 3, 'darsh.hegde@university.edu'),
('IT2021005', 'Veer Kamath', 6, 3, 'veer.kamath@university.edu'),
('IT2021006', 'Yash Kumar', 6, 3, 'yash.kumar@university.edu'),
('IT2021007', 'Laksh Shenoy', 6, 3, 'laksh.shenoy@university.edu'),
('IT2021008', 'Kartik Rao', 6, 3, 'kartik.rao@university.edu'),
('IT2021009', 'Nirvaan Pai', 6, 3, 'nirvaan.pai@university.edu'),
('IT2021010', 'Raghav Nayak', 6, 3, 'raghav.nayak@university.edu'),
('IT2021011', 'Aaradhya Kulkarni', 6, 3, 'aaradhya.kulkarni@university.edu'),
('IT2021012', 'Ahana Joshi', 6, 3, 'ahana.joshi@university.edu'),
('IT2021013', 'Aria Patil', 6, 3, 'aria.patil@university.edu'),
('IT2022001', 'Rishaan Deshpande', 6, 2, 'rishaan.deshpande@university.edu'),
('IT2022002', 'Tanish Shinde', 6, 2, 'tanish.shinde@university.edu'),
('IT2022003', 'Advay Deshmukh', 6, 2, 'advay.deshmukh@university.edu'),
('IT2022004', 'Aarush Kale', 6, 2, 'aarush.kale@university.edu'),
('IT2022005', 'Vivaan Tambe', 6, 2, 'vivaan.tambe@university.edu'),
('IT2022006', 'Shlok Jadhav', 6, 2, 'shlok.jadhav@university.edu'),
('IT2022007', 'Parth Sawant', 6, 2, 'parth.sawant@university.edu'),
('IT2022008', 'Kavya More', 6, 2, 'kavya.more@university.edu'),
('IT2022009', 'Mahi Bhosale', 6, 2, 'mahi.bhosale@university.edu'),
('IT2022010', 'Nitya Salvi', 6, 2, 'nitya.salvi@university.edu'),
('IT2022011', 'Mahika Wagh', 6, 2, 'mahika.wagh@university.edu'),
('IT2022012', 'Krisha Pawar', 6, 2, 'krisha.pawar@university.edu'),

-- CHEM Department (10 students)
('CHEM2021001', 'Arhaan Malhotra', 7, 3, 'arhaan.malhotra@university.edu'),
('CHEM2021002', 'Ayaan Khanna', 7, 3, 'ayaan.khanna@university.edu'),
('CHEM2021003', 'Zayan Batra', 7, 3, 'zayan.batra@university.edu'),
('CHEM2021004', 'Vanya Arora', 7, 3, 'vanya.arora@university.edu'),
('CHEM2021005', 'Janvi Grover', 7, 3, 'janvi.grover@university.edu'),
('CHEM2022001', 'Reyansh Talwar', 7, 2, 'reyansh.talwar@university.edu'),
('CHEM2022002', 'Shivansh Kohli', 7, 2, 'shivansh.kohli@university.edu'),
('CHEM2022003', 'Viaan Sethi', 7, 2, 'viaan.sethi@university.edu'),
('CHEM2022004', 'Hiya Bajaj', 7, 2, 'hiya.bajaj@university.edu'),
('CHEM2022005', 'Reet Dhawan', 7, 2, 'reet.dhawan@university.edu'),

-- BT Department (10 students)
('BT2021001', 'Ansh Suri', 8, 3, 'ansh.suri@university.edu'),
('BT2021002', 'Hriday Kapur', 8, 3, 'hriday.kapur@university.edu'),
('BT2021003', 'Shaurya Vohra', 8, 3, 'shaurya.vohra@university.edu'),
('BT2021004', 'Myra Ahuja', 8, 3, 'myra.ahuja@university.edu'),
('BT2021005', 'Anika Mehra', 8, 3, 'anika.mehra@university.edu'),
('BT2022001', 'Arjun Anand', 8, 2, 'arjun.anand2@university.edu'),
('BT2022002', 'Vihaan Mittal', 8, 2, 'vihaan.mittal2@university.edu'),
('BT2022003', 'Aditya Chadha', 8, 2, 'aditya.chadha2@university.edu'),
('BT2022004', 'Anaya Oberoi', 8, 2, 'anaya.oberoi@university.edu'),
('BT2022005', 'Pari Luthra', 8, 2, 'pari.luthra@university.edu');

-- ===============================================
-- Insert Halls
-- ===============================================
INSERT INTO halls (hall_code, name, capacity, rows, columns, building, floor) VALUES
('H1', 'Main Examination Hall 1', 100, 10, 10, 'Academic Block A', 1),
('H2', 'Main Examination Hall 2', 80, 10, 8, 'Academic Block A', 2),
('H3', 'Engineering Block Hall', 60, 10, 6, 'Engineering Block', 1),
('H4', 'Science Block Hall', 50, 10, 5, 'Science Block', 2),
('H5', 'Auditorium Hall', 120, 12, 10, 'Administrative Block', 0);

-- ===============================================
-- Insert Invigilators
-- ===============================================
INSERT INTO invigilators (employee_id, name, department_id, email, phone) VALUES
('EMP001', 'Dr. Rajesh Kumar', 1, 'rajesh.kumar@university.edu', '9876543210'),
('EMP002', 'Prof. Sunita Sharma', 2, 'sunita.sharma@university.edu', '9876543211'),
('EMP003', 'Dr. Amit Patel', 3, 'amit.patel@university.edu', '9876543212'),
('EMP004', 'Prof. Priya Singh', 4, 'priya.singh@university.edu', '9876543213'),
('EMP005', 'Dr. Vikram Gupta', 5, 'vikram.gupta@university.edu', '9876543214'),
('EMP006', 'Prof. Neha Verma', 6, 'neha.verma@university.edu', '9876543215'),
('EMP007', 'Dr. Rahul Nair', 7, 'rahul.nair@university.edu', '9876543216'),
('EMP008', 'Prof. Anjali Reddy', 8, 'anjali.reddy@university.edu', '9876543217'),
('EMP009', 'Dr. Suresh Joshi', 1, 'suresh.joshi@university.edu', '9876543218'),
('EMP010', 'Prof. Kavita Desai', 2, 'kavita.desai@university.edu', '9876543219'),
('EMP011', 'Dr. Manish Iyer', 3, 'manish.iyer@university.edu', '9876543220'),
('EMP012', 'Prof. Deepa Menon', 4, 'deepa.menon@university.edu', '9876543221'),
('EMP013', 'Dr. Anil Krishnan', 5, 'anil.krishnan@university.edu', '9876543222'),
('EMP014', 'Prof. Rekha Rao', 6, 'rekha.rao@university.edu', '9876543223'),
('EMP015', 'Dr. Sanjay Kapoor', 7, 'sanjay.kapoor@university.edu', '9876543224');

-- ===============================================
-- Insert Sample Exams
-- ===============================================
INSERT INTO exams (exam_code, subject, exam_date, start_time, end_time, duration, status) VALUES
('EX2024001', 'Data Structures and Algorithms', '2024-05-15', '09:00:00', '12:00:00', 180, 'SCHEDULED'),
('EX2024002', 'Database Management Systems', '2024-05-17', '14:00:00', '17:00:00', 180, 'SCHEDULED'),
('EX2024003', 'Digital Electronics', '2024-05-20', '09:00:00', '12:00:00', 180, 'SCHEDULED');

-- ===============================================
-- Link Students to Exam (All CSE and IT students for first exam)
-- ===============================================
INSERT INTO exam_students (exam_id, student_id)
SELECT 1, id FROM students WHERE department_id IN (1, 6);

-- Link ECE and EEE students to second exam
INSERT INTO exam_students (exam_id, student_id)
SELECT 2, id FROM students WHERE department_id IN (2, 5);

-- Link ME, CE, CHEM, BT students to third exam
INSERT INTO exam_students (exam_id, student_id)
SELECT 3, id FROM students WHERE department_id IN (3, 4, 7, 8);

-- ===============================================
-- Initialize Invigilator Workload
-- ===============================================
INSERT INTO invigilator_workload (invigilator_id, total_assignments)
SELECT id, 0 FROM invigilators;
