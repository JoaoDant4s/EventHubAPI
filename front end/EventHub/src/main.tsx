import './index.css'
import React, { ReactNode } from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Login from './assets/routes/home/Login.tsx'
import Dashboard from './assets/routes/dashboard/Dashboard.tsx'
import Register from './assets/routes/home/Register.tsx'
import { IconDefinition } from '@fortawesome/fontawesome-svg-core'
import NextEvents from './assets/routes/dashboard/nextEvents/NextEvents.tsx'
import { faCreditCard, faList, faTicket, faUser } from '@fortawesome/free-solid-svg-icons'
import Profile from './assets/routes/dashboard/profile/Profile.tsx'
import UpdateProfile from './assets/routes/dashboard/profile/UpdateProfile.tsx'
import CreditCard from './assets/routes/dashboard/creditCard/CreditCard.tsx'
import RegisterCreditCard from './assets/routes/dashboard/creditCard/RegisterCreditCard.tsx'
import ParticipantRegistration from './assets/routes/dashboard/users/ParticipantRegistration.tsx'
import AttractionRegistration from './assets/routes/dashboard/users/AttractionRegistration.tsx'
import PromoterRegistration from './assets/routes/dashboard/users/PromoterRegistration.tsx'
import Users from './assets/routes/dashboard/users/users.tsx'
import UpdateUser from './assets/routes/dashboard/users/UpdateUser.tsx'

interface PrivateRouteProps {
  redirect: string;
  children: React.ReactNode;
}

const PrivateRoute=({redirect, children}:PrivateRouteProps)=>{
  const authenticated:boolean = localStorage.getItem("token") !== null;
  return authenticated ? children : <Navigate to={redirect} />;
}

export type Role = "participant" | "admin" | "promoter" | "attraction" | string;

export interface NavItem {
    text?:string;
    icon?:IconDefinition;
    pathRoot:string;
    path:string;
    component:ReactNode;
    permission:Array<Role>;
    navbar?:boolean
}

const routes:Array<NavItem> = [
  {text:"Dashboard", icon:faList, pathRoot:"/dashboard", path:"/dashboard", component: <NextEvents />, permission:["participant","admin","attraction"], navbar:true},
  {text:"Meus eventos", icon:faList, pathRoot:"/dashboard", path:"/dashboard", component: <NextEvents/>, permission:["promoter"], navbar:true},
  {text:"Perfil", icon:faUser, pathRoot:"/dashboard/profile", path:"/dashboard/profile", component: <Profile/>, permission:["participant","admin","attraction","promoter"], navbar:true},
  {text:"Meus ingressos", pathRoot:"/dashboard/myTickets", icon:faTicket, path:"/dashboard/myTickets", component: <NextEvents/>, permission:["participant","admin","attraction"], navbar:true},
  {text:"Cartão de crédito", pathRoot:"/dashboard/creditCard", icon:faCreditCard, path:"/dashboard/creditCard", component: <CreditCard/>, permission:["participant","admin","attraction"], navbar:true},
  {text:"Eventos participados", icon:faList, pathRoot:"/dashboard/pastEvents", path:"/dashboard/pastEvents", component: <NextEvents/>, permission:["attraction"], navbar:true},  
  {text:"Usuário", icon:faList, pathRoot:"/admin/users", path:"/admin/users", component: <Users/>, permission:["admin"], navbar:true},
  {text:"Eventos", icon:faList, pathRoot:"/admin/events", path:"/admin/events", component: <NextEvents/>, permission:["admin"], navbar:true},
  
  // AS NOVAS ROTAS SÃO COLOCADAS AQUI. DEFINA UM OBJETOS DO TIPO NAVITEM

  {pathRoot:"/dashboard/profile", path:"/dashboard/profile/updateProfile", component: <UpdateProfile/>, permission:["participant","admin","attraction","promoter"]},
  {pathRoot:"/dashboard/creditCard", path:"/dashboard/creditCard/registerCreditCard", component: <RegisterCreditCard/>, permission:["participant","admin","attraction"], navbar:false},
  {pathRoot:"/admin/users", path:"/admin/users/participantRegistration", component: <ParticipantRegistration/>, permission:["admin"], navbar:false},
  {pathRoot:"/admin/users", path:"/admin/users/updateUser/:id", component: <UpdateUser/>, permission:["admin"], navbar:false},
  {pathRoot:"/admin/users", path:"/admin/users/attractionRegistration", component: <AttractionRegistration/>, permission:["admin"], navbar:false},
  {pathRoot:"/admin/users", path:"/admin/users/promoterRegistration", component: <PromoterRegistration/>, permission:["admin"], navbar:false},
]

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        {/* PRIVATE ROUTES */}
        {
          routes.map((route, index)=>{
            return (
              <Route
                key={`route-${index}`}
                path={route.path}
                element={<PrivateRoute redirect='/'><Dashboard routes={routes} /></PrivateRoute>}
              />
            )})
        }
        {/* PUBLIC ROUTES */}
        <Route path='/' element={<Login/>} />
        <Route path='/register/' element={<Register/>} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
)
