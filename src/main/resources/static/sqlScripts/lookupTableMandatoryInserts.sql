-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 08, 2022 at 12:22 PM
-- Server version: 10.2.22-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `theBridge`
--

--
-- Dumping data for table `admin_position`
--

INSERT INTO `admin_position` (`id`, `position`) VALUES
(1, 'Principal'),
(2, 'Teacher'),
(3, 'Professor'),
(4, 'Instructor'),
(5, 'Tech'),
(6, 'N/A'),
(7, 'Teacher\'s Aide'),
(8, 'Counselor'),
(9, 'Volunteer'),
(10, 'Vice Principal');

--
-- Dumping data for table `course_genre`
--

INSERT INTO `course_genre` (`id`, `genre`) VALUES
(1, 'Language Arts'),
(2, 'Numerical Applications'),
(3, 'Philosphy'),
(4, 'Science'),
(5, 'Psychology'),
(6, 'Humanities'),
(7, 'Sociology'),
(8, 'Moral Reasoning');

--
-- Dumping data for table `course_level`
--

INSERT INTO `course_level` (`id`, `level`) VALUES
(1, '100 Level'),
(2, '200 Level'),
(3, '300 Level'),
(4, '10-12th Grade Level');

--
-- Dumping data for table `form_name`
--

INSERT INTO `form_name` (`id`, `display_name`, `form_nm`) VALUES
(1, 'Create Admin', 'createAdmin'),
(2, 'Create Student', 'createStudent'),
(3, 'Add Admin Position', 'addAdminPosition'),
(4, 'Add School', 'addSchool'),
(5, 'Create Class', 'createClass'),
(6, 'Create Course', 'createCourse'),
(7, 'Add Courses to School', 'addCoursesToSchool'),
(8, 'Add Course Genre', 'addCourseGenre'),
(9, 'Add Course Level', 'addCourseLevel'),
(10, 'Add Grade Level', 'addGradeLevel'),
(11, 'Add Status', 'addStatus'),
(12, 'Add Relationship Type', 'addRelationshipType'),
(13, 'Add an Assignment', 'addAssignment'),
(14, 'Add School Year', 'addSchoolYear'),
(15, 'Create Roster', 'createRoster'),
(16, 'Grade an Assignment', 'getSchool'),
(17, 'Edit Admin', 'getAdmin');

--
-- Dumping data for table `report_name`
--

INSERT INTO `report_name` (`id`, `display_name`, `rpt_name`) VALUES
(1, 'All Admins', 'admin'),
(2, 'Students', 'students'),
(3, 'All Students (By school)', 'allStudentsBySchool'),
(4, 'Schools', 'schools'),
(5, 'School Links', 'schoolLinks'),
(6, 'Course List', 'courseList'),
(7, 'All Admins (Unemployed)', 'adminsUnemployed'),
(8, 'All Classes', 'classes'),
(10, 'All Class Assignments', 'assignments'),
(12, 'All Class Rosters', 'rosters');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
