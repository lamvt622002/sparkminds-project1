import React from "react";
import { Layout, Menu } from "antd";
import HeaderLayout from "components/layouts/HeaderLayout";
import styles from './Layout.module.scss'
import useMainLayout from "./useMainLayout";
import { CardMedia } from "@mui/material";
import { CONFIG } from "common/config";
const { Header, Content, Sider } = Layout

export default function MainLayout({children}:{children:React.ReactNode}){
    const {items, current, collapsed, setCollapsed} = useMainLayout();
    return(
        <Layout className={styles.layout}>
            <Sider className={styles.sider} collapsible onCollapse={(value) =>{setCollapsed(value)}}>
                {
                    collapsed? (
                        <CardMedia className={styles.logoMini} component="img" image={CONFIG.IMAGES.SPARKMINDS_LOGO_MINI}/>
                    ):(
                         <CardMedia className={styles.logo} component="img" image={CONFIG.IMAGES.SPARKMINDS_LOGO}/>
                    )
                }
                <Menu
                    className={styles.menuSider}
                    theme="dark"
                    defaultSelectedKeys={['1']}
                    mode="inline"
                    items={items}
                    selectedKeys={[current]}
                />
            </Sider>
            <Layout className={styles.children}>
                <Header className={styles.header}>
                    <HeaderLayout/>
                </Header>
                <Content className={styles.content}>
                    {children}
                </Content>
            </Layout>
        </Layout>

    )
}