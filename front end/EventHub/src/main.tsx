import './index.css'
import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Login from './assets/routes/home/Login.tsx'
import Dashboard from './assets/routes/dashboard/Dashboard.tsx'
interface PrivateRouteProps {
  redirect: string;
  children: React.ReactNode;
}

const PrivateRoute=({redirect, children}:PrivateRouteProps)=>{
  const authenticated:boolean = localStorage.getItem("token") !== null;
  return authenticated ? children : <Navigate to={redirect} />;
}

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        {/* PRIVATE ROUTES */}
        <Route path='/dashboard' element={<PrivateRoute redirect='/'>
          <Dashboard/>
        </PrivateRoute>} />
        {/* PUBLIC ROUTES */}
        <Route path='/' element={<Login/>} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
)
