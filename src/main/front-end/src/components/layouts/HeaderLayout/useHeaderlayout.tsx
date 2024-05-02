import LogoutIcon from '@mui/icons-material/Logout'
import SettingsIcon from '@mui/icons-material/Settings'
import type { MenuProps } from 'antd'
import { CONFIG } from 'common/config';
import { useNavigate } from 'react-router-dom'
import userStore from 'stores/user'
import { logout } from 'api/user';
import { toast } from 'react-toastify';
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
                const res = toast.promise(logout(), {pending:"Waiting for logout"});
                console.log(res);
                // userStore.getState().logout()
                // navigate(CONFIG.PAGE_URLS.LOGIN)
            }
        },

    ]
    return {items}
}
export default useHeaderLayout;