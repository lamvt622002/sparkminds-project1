import React, { useLayoutEffect } from "react"
import { MenuProps } from "antd"
import { Link } from "react-router-dom"
import AdUnitsIcon from '@mui/icons-material/AdUnits'

const useMainLayout = () => {
    const [current, setCurrent] = React.useState('sidebar1')
    const [collapsed, setCollapsed] = React.useState(false);
    const items: MenuProps['items'] = [
        {
            label: <Link to={"#"}>Sidebar 1</Link>,
            key:'sidebar1',
            icon:<AdUnitsIcon/>
        },
        {
            label: <Link to={"#"}>Sidebar 2</Link>,
            key:'sidebar2',
            icon:<AdUnitsIcon/>
        },
        {
            label: <Link to={"#"}>Sidebar 3</Link>,
            key:'sidebar3',
            icon:<AdUnitsIcon/>
        },
        {
            label: <Link to={"#"}>Sidebar 4</Link>,
            key:'sidebar4',
            icon:<AdUnitsIcon/>
        },
        {
            label: <Link to={"#"}>Sidebar 5</Link>,
            key:'sidebar5',
            icon:<AdUnitsIcon/>
        },
        {
            label: <Link to={"#"}>Sidebar 6</Link>,
            key:'sidebar6',
            icon:<AdUnitsIcon/>
        }
    ]

    // React.useEffect(() => {

    // })
    return {items, current, collapsed, setCollapsed}
}
export default useMainLayout;