import { Box } from "@mui/material";
import HomePage from "pages/home";
import {Navigate, Route, Routes } from "react-router-dom";

export default function RegularRoute(){
    return(
        <Box>
            <Routes>
                <Route path="home" element={<HomePage/>}/>
            </Routes>
        </Box>
    )
}