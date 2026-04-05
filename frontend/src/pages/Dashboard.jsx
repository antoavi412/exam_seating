import React, { useState, useEffect } from 'react';
import { dashboardAPI } from '../services/api';

export default function Dashboard() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchStats();
  }, []);

  const fetchStats = async () => {
    try {
      const response = await dashboardAPI.getStats();
      setStats(response.data);
    } catch (error) {
      console.error('Error fetching stats:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="flex justify-center items-center h-64">Loading...</div>;
  }

  const statCards = [
    { label: 'Total Students', value: stats?.totalStudents || 0, icon: '👨‍🎓', color: 'bg-blue-500' },
    { label: 'Total Halls', value: stats?.totalHalls || 0, icon: '🏛️', color: 'bg-green-500' },
    { label: 'Total Invigilators', value: stats?.totalInvigilators || 0, icon: '👨‍🏫', color: 'bg-purple-500' },
    { label: 'Total Exams', value: stats?.totalExams || 0, icon: '📝', color: 'bg-yellow-500' },
    { label: 'Scheduled Exams', value: stats?.scheduledExams || 0, icon: '📅', color: 'bg-indigo-500' },
    { label: 'Completed Exams', value: stats?.completedExams || 0, icon: '✅', color: 'bg-teal-500' },
  ];

  return (
    <div className="px-4 sm:px-6 lg:px-8">
      <div className="sm:flex sm:items-center">
        <div className="sm:flex-auto">
          <h1 className="text-3xl font-semibold text-gray-900">Dashboard</h1>
          <p className="mt-2 text-sm text-gray-700">
            Overview of the exam seating arrangement system
          </p>
        </div>
      </div>

      <div className="mt-8 grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-3">
        {statCards.map((stat, index) => (
          <div
            key={index}
            className="bg-white overflow-hidden shadow rounded-lg hover:shadow-lg transition-shadow"
          >
            <div className="p-5">
              <div className="flex items-center">
                <div className={`flex-shrink-0 ${stat.color} rounded-md p-3`}>
                  <span className="text-3xl">{stat.icon}</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      {stat.label}
                    </dt>
                    <dd className="flex items-baseline">
                      <div className="text-2xl font-semibold text-gray-900">
                        {stat.value}
                      </div>
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="mt-8 bg-white shadow rounded-lg p-6">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">System Information</h2>
        <div className="space-y-3">
          <div className="flex justify-between items-center">
            <span className="text-gray-600">Total Hall Capacity:</span>
            <span className="font-semibold text-lg">{stats?.totalHallCapacity || 0} seats</span>
          </div>
          <div className="flex justify-between items-center">
            <span className="text-gray-600">Utilization Rate:</span>
            <span className="font-semibold text-lg">
              {stats?.totalHallCapacity > 0
                ? Math.round((stats.totalStudents / stats.totalHallCapacity) * 100)
                : 0}
              %
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}
