import { Box } from "@mui/material";
import HomePage from "pages/home";
import ChangeEmail from "pages/profile/ChangeEmail";
import ChangePassword from "pages/profile/ChangePassword";
import ChangePhone from "pages/profile/ChangePhone";
import {Navigate, Route, Routes } from "react-router-dom";

export default function RegularRoute(){
    return(
        <Box>
            <Routes>
                <Route path="home" element={<HomePage/>}/>
                <Route path="/profile/change-email" element={<ChangeEmail/>} />
                <Route path="/profile/change-phone" element={<ChangePhone/>} />
                <Route path="/profile/change-password" element={<ChangePassword/>} />
            </Routes>
        </Box>
    )
}