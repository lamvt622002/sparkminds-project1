import { Box, Typography } from "@mui/material";
import useStore from "stores/user";
import { test } from "api/auth";
export default function HomePage(){
    const {user} = useStore();

    const handleTest = async () => {
        const res = await test();
        console.log(res);
    }
    handleTest();

    return(
        <Box>
            <Typography variant="body2">
                hello {user?.firstName} {user?.lastName}
            </Typography>
        </Box>
    )
}