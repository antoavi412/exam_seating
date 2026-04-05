import React, { useState, useEffect } from 'react';
import { examAPI } from '../services/api';

export default function Exams() {
  const [exams, setExams] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchExams();
  }, []);

  const fetchExams = async () => {
    try {
      const response = await examAPI.getAll();
      setExams(response.data);
    } catch (error) {
      console.error('Error fetching exams:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleAllocateSeating = async (examId) => {
    if (window.confirm('Allocate seating for this exam? This will override existing allocations.')) {
      try {
        await examAPI.allocateSeating(examId);
        alert('Seating allocated successfully!');
      } catch (error) {
        console.error('Error allocating seating:', error);
        alert(error.response?.data?.error || 'Failed to allocate seating');
      }
    }
  };

  const handleAllocateInvigilators = async (examId) => {
    if (window.confirm('Assign invigilators for this exam?')) {
      try {
        await examAPI.allocateInvigilators(examId);
        alert('Invigilators assigned successfully!');
      } catch (error) {
        console.error('Error assigning invigilators:', error);
        alert(error.response?.data?.error || 'Failed to assign invigilators');
      }
    }
  };

  if (loading) return <div className="flex justify-center py-8">Loading...</div>;

  return (
    <div className="px-4 sm:px-6 lg:px-8">
      <div className="sm:flex sm:items-center mb-8">
        <div className="sm:flex-auto">
          <h1 className="text-3xl font-semibold text-gray-900">Examinations</h1>
          <p className="mt-2 text-sm text-gray-700">
            Manage exams and allocate seating arrangements
          </p>
        </div>
      </div>

      <div className="space-y-4">
        {exams.map((exam) => (
          <div key={exam.id} className="bg-white shadow rounded-lg overflow-hidden">
            <div className="px-6 py-4">
              <div className="flex items-center justify-between">
                <div className="flex-1">
                  <h3 className="text-lg font-semibold text-gray-900">{exam.subject}</h3>
                  <p className="text-sm text-gray-500 mt-1">Code: {exam.examCode}</p>
                </div>
                <div className={`px-3 py-1 rounded-full text-xs font-medium ${
                  exam.status === 'SCHEDULED' ? 'bg-blue-100 text-blue-800' :
                  exam.status === 'ONGOING' ? 'bg-yellow-100 text-yellow-800' :
                  exam.status === 'COMPLETED' ? 'bg-green-100 text-green-800' :
                  'bg-gray-100 text-gray-800'
                }`}>
                  {exam.status}
                </div>
              </div>
              <div className="mt-4 grid grid-cols-2 md:grid-cols-4 gap-4">
                <div>
                  <p className="text-xs text-gray-500">Date</p>
                  <p className="text-sm font-medium">{exam.examDate}</p>
                </div>
                <div>
                  <p className="text-xs text-gray-500">Time</p>
                  <p className="text-sm font-medium">{exam.startTime} - {exam.endTime}</p>
                </div>
                <div>
                  <p className="text-xs text-gray-500">Duration</p>
                  <p className="text-sm font-medium">{exam.duration} minutes</p>
                </div>
                <div>
                  <p className="text-xs text-gray-500">Students</p>
                  <p className="text-sm font-medium">{exam.totalStudents || 0}</p>
                </div>
              </div>
              <div className="mt-4 flex space-x-3">
                <button
                  onClick={() => handleAllocateSeating(exam.id)}
                  className="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  Allocate Seating
                </button>
                <button
                  onClick={() => handleAllocateInvigilators(exam.id)}
                  className="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500"
                >
                  Assign Invigilators
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
