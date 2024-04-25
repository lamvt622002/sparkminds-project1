import { Box, Typography } from "@mui/material";
import Table from "components/Table";


import useStore from "stores/user";
import { test } from "api/auth";
import useHome from "./useHome";
import MainLayout from "components/layouts/MainLayout";
import Cookies from "js-cookie";


export default function HomePage(){
    // const {user} = useStore();

    const handleTest = async () => {
        const res = await test();
        const headers = res.headers as Record<string, string | string[] | undefined>;
        const setCookieHeader = headers['set-cookie'];
        console.log(res);
        console.log(setCookieHeader);
    }
    handleTest();

    console.log(Cookies.get());

    const {columns, books, loading, handleOnCellClick, totalPage, currentPage} = useHome()

    return(
        <MainLayout>
            <Typography variant="body2">
                hi
            </Typography>
            <Table columns={columns} rows={books} loading={loading} handleOnCellClick={handleOnCellClick} totalPage={totalPage} currentPage={currentPage}/>
        </MainLayout>
       
    )
}