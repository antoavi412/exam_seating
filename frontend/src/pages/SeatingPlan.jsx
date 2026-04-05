import React, { useState, useEffect } from 'react';
import { examAPI } from '../services/api';

export default function SeatingPlan() {
  const [exams, setExams] = useState([]);
  const [selectedExam, setSelectedExam] = useState('');
  const [seating, setSeating] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchExams();
  }, []);

  const fetchExams = async () => {
    try {
      const response = await examAPI.getAll();
      setExams(response.data);
    } catch (error) {
      console.error('Error fetching exams:', error);
    }
  };

  const fetchSeating = async (examId) => {
    setLoading(true);
    try {
      const response = await examAPI.getSeating(examId);
      setSeating(response.data);
    } catch (error) {
      console.error('Error fetching seating:', error);
      alert('Failed to fetch seating plan');
    } finally {
      setLoading(false);
    }
  };

  const handleExamChange = (e) => {
    const examId = e.target.value;
    setSelectedExam(examId);
    if (examId) {
      fetchSeating(examId);
    } else {
      setSeating([]);
    }
  };

  // Group seating by hall
  const groupedSeating = seating.reduce((acc, seat) => {
    if (!acc[seat.hallCode]) {
      acc[seat.hallCode] = {
        hallName: seat.hallName,
        seats: []
      };
    }
    acc[seat.hallCode].seats.push(seat);
    return acc;
  }, {});

  return (
    <div className="px-4 sm:px-6 lg:px-8">
      <div className="sm:flex sm:items-center mb-8">
        <div className="sm:flex-auto">
          <h1 className="text-3xl font-semibold text-gray-900">Seating Plan</h1>
          <p className="mt-2 text-sm text-gray-700">
            View seating arrangements for exams
          </p>
        </div>
      </div>

      <div className="mb-6">
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Select Exam
        </label>
        <select
          value={selectedExam}
          onChange={handleExamChange}
          className="block w-full md:w-96 rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm px-3 py-2 border"
        >
          <option value="">Choose an exam...</option>
          {exams.map((exam) => (
            <option key={exam.id} value={exam.id}>
              {exam.subject} - {exam.examCode} ({exam.examDate})
            </option>
          ))}
        </select>
      </div>

      {loading && <div className="text-center py-8">Loading seating plan...</div>}

      {!loading && seating.length === 0 && selectedExam && (
        <div className="text-center py-8 text-gray-500">
          No seating allocation found for this exam. Please allocate seating first.
        </div>
      )}

      {!loading && seating.length > 0 && (
        <div className="space-y-8">
          {Object.entries(groupedSeating).map(([hallCode, hallData]) => (
            <div key={hallCode} className="bg-white shadow rounded-lg overflow-hidden">
              <div className="px-6 py-4 bg-indigo-50 border-b border-indigo-100">
                <h2 className="text-xl font-semibold text-indigo-900">
                  {hallData.hallName} ({hallCode})
                </h2>
                <p className="text-sm text-indigo-600 mt-1">
                  {hallData.seats.length} students allocated
                </p>
              </div>
              <div className="px-6 py-4">
                <div className="overflow-x-auto">
                  <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                      <tr>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">
                          Seat
                        </th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">
                          Roll Number
                        </th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">
                          Name
                        </th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">
                          Department
                        </th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">
                          Position
                        </th>
                      </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                      {hallData.seats.sort((a, b) => a.seatNumber.localeCompare(b.seatNumber)).map((seat) => (
                        <tr key={seat.id} className="hover:bg-gray-50">
                          <td className="px-4 py-3 whitespace-nowrap text-sm font-medium text-indigo-600">
                            {seat.seatNumber}
                          </td>
                          <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-900">
                            {seat.studentRollNumber}
                          </td>
                          <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-900">
                            {seat.studentName}
                          </td>
                          <td className="px-4 py-3 whitespace-nowrap text-sm">
                            <span className="px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800">
                              {seat.departmentCode}
                            </span>
                          </td>
                          <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-500">
                            Row {seat.rowNumber}, Col {seat.columnNumber}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
