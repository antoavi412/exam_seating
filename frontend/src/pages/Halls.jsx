import React, { useState, useEffect } from 'react';
import { hallAPI } from '../services/api';

export default function Halls() {
  const [halls, setHalls] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchHalls();
  }, []);

  const fetchHalls = async () => {
    try {
      const response = await hallAPI.getAll();
      setHalls(response.data);
    } catch (error) {
      console.error('Error fetching halls:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="flex justify-center py-8">Loading...</div>;

  return (
    <div className="px-4 sm:px-6 lg:px-8">
      <div className="sm:flex sm:items-center mb-8">
        <div className="sm:flex-auto">
          <h1 className="text-3xl font-semibold text-gray-900">Examination Halls</h1>
          <p className="mt-2 text-sm text-gray-700">
            Manage examination halls and their seating capacity
          </p>
        </div>
      </div>

      <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
        {halls.map((hall) => (
          <div key={hall.id} className="bg-white overflow-hidden shadow rounded-lg">
            <div className="px-4 py-5 sm:p-6">
              <div className="flex items-center justify-between">
                <div className="flex-1">
                  <h3 className="text-lg font-medium text-gray-900">{hall.name}</h3>
                  <p className="text-sm text-gray-500 mt-1">Code: {hall.hallCode}</p>
                </div>
                <div
                  className={`ml-4 flex-shrink-0 ${
                    hall.isActive ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                  } px-2 py-1 rounded-full text-xs font-medium`}
                >
                  {hall.isActive ? 'Active' : 'Inactive'}
                </div>
              </div>
              <div className="mt-4 grid grid-cols-2 gap-4">
                <div>
                  <p className="text-xs text-gray-500">Capacity</p>
                  <p className="text-xl font-semibold text-indigo-600">{hall.capacity}</p>
                </div>
                <div>
                  <p className="text-xs text-gray-500">Layout</p>
                  <p className="text-xl font-semibold text-gray-900">
                    {hall.rows} × {hall.columns}
                  </p>
                </div>
                <div>
                  <p className="text-xs text-gray-500">Building</p>
                  <p className="text-sm font-medium text-gray-900">{hall.building}</p>
                </div>
                <div>
                  <p className="text-xs text-gray-500">Floor</p>
                  <p className="text-sm font-medium text-gray-900">{hall.floor}</p>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
