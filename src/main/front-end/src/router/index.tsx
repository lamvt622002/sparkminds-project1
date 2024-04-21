import React from "react";
import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom'

import Login from "pages/auth/Login";
import Register from "pages/auth/Register";
import VerifyEmail from "pages/auth/VerifyEmail";
import ForgotPassword from "pages/auth/ForgotPassword";
import ChangePassword from "pages/auth/ChangePassword";
import useStore from "stores/user"
import RegularRoute from "./RegularRoute";
import { Status } from "types/IAccount";
import HomePage from "pages/home";
import MainLayout from "components/layouts/MainLayout";
export default function WebRoute(){
    const {user} = useStore();

    return(
        <BrowserRouter>
            <Routes>
                {/* Authen user */}
                <Route path="login" element={user?  <Navigate to={"/home"}/> : <Login/>}/>
                <Route path="register" element={user? <Navigate to={"/home"}/> : <Register/>}/>
                <Route path="forgot-password" element={user? <Navigate to={"/home"}/> : <ForgotPassword/>}/>
                <Route path="change-password" element={user? <Navigate to={"/home"}/> : <ChangePassword/>}/>
                <Route path="verify-email" element={user? <Navigate to={"/home"}/> : <VerifyEmail/>}/>
                {/* regular route*/}
                <Route path="dash-board" element={<HomePage/>}/>
                <Route  path="/*" element={user? <RegularRoute/> : <Navigate to={"/login"}/>}/>
            </Routes>
        </BrowserRouter>
    )
}