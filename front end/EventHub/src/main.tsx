import './index.css'
import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Login from './assets/routes/home/Login.tsx'
import Dashboard from './assets/routes/dashboard/Dashboard.tsx'
import Register from './assets/routes/home/Register.tsx'
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
        <Route 
          path='/dashboard'
          element={<PrivateRoute redirect='/'><Dashboard/></PrivateRoute>}
        />
        <Route 
          path='/dashboard/profile'
          element={<PrivateRoute redirect='/'><Dashboard/></PrivateRoute>}
        />
        <Route 
          path='/dashboard/myTickets'
          element={<PrivateRoute redirect='/'><Dashboard/></PrivateRoute>}
        />
        <Route 
          path='/dashboard/creditCard'
          element={<PrivateRoute redirect='/'><Dashboard/></PrivateRoute>}
        />
        <Route 
          path='/admin/users'
          element={<PrivateRoute redirect='/'><Dashboard/></PrivateRoute>}
        />
        <Route 
          path='/admin/events'
          element={<PrivateRoute redirect='/'><Dashboard/></PrivateRoute>}
        />
        {/* PUBLIC ROUTES */}
        <Route path='/' element={<Login/>} />
        <Route path='/register/' element={<Register/>} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
)
