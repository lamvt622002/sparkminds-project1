import LogoutIcon from '@mui/icons-material/Logout'
import SettingsIcon from '@mui/icons-material/Settings'
import type { MenuProps } from 'antd'
import { CONFIG } from 'common/config';
import { useNavigate } from 'react-router-dom'
import userStore from 'stores/user'
const useHeaderLayout = () => {
    const navigate = useNavigate();
    const items:MenuProps['items'] = [
        {
            key:'1',
            label:'Setting',
            icon:<SettingsIcon/>,
            onClick: () => {
                navigate(CONFIG.PAGE_URLS.ACCOUNT_SETTING)
            }
        },
        {
            key:'2',
            label:'Logout',
            icon:<LogoutIcon/>,
            onClick: () => {
                userStore.getState().logout()
                navigate(CONFIG.PAGE_URLS.ACCOUNT_SETTING)
            }
        },

    ]
    return {items}
}
export default useHeaderLayout;