import { Box, Typography } from "@mui/material";
import styles from "./HeaderLayout.module.scss"
import { Avatar, Dropdown } from "antd";
import { CONFIG } from "common/config";
import useHeaderLayout from "./useHeaderlayout";

export default function HeaderLayout(){
    const {items} = useHeaderLayout()
    return (
        <Box className={styles.layout} >
            <Dropdown placement="bottomLeft" trigger={['click']} menu={{items}}>
                <Box className={styles.flew_row}>
                    <Typography variant="body1">
                        Vu Lam
                    </Typography>
                    <Avatar src={CONFIG.IMAGES.SPARKMINDS_LOGO_MINI}/>
                </Box>
            </Dropdown>
        </Box>
    )
}