import { devtools, persist } from 'zustand/middleware'
import AsyncStorage from '@react-native-async-storage/async-storage'
import axios, { AxiosError, AxiosResponse } from 'axios'
import { create } from 'zustand'
import { IUser } from 'types/IAccount'
import { toast } from 'react-toastify'
const baseApi = process.env.REACT_APP_API_ENDPOINT

const useUserStore = (set: any, get: any) => ({
  user: null as IUser | null,
  token: '',
  getUser: () => null,
  setDataUser: (data: IUser) => {
    set((state: any) => ({
      ...state,
      user: data,
      token: data.accessToken,
    }))
  },
  logout: () => {
    set(() => ({
      user: null,
      token: '', 
    }))
  },

  refreshToken: async () => {
    try{
      await axios
      .post(`${baseApi}api/auth/refresh-token`, {
        refreshToken: get().user?.refreshToken,
      })
      .then(async (res) => {
        if (res?.data?.data) {
          await set((state: any) => ({
            ...state,
            user: {
              ...state.user,
              accessToken: res.data.data.accessToken,
            },
            token: res.data.data.accessToken,
          }))
          get().setDataUser(get().user)
        } else{
          get().logout()
        }
      })
    }catch(error){
      if ((error as AxiosError).response) {
        if ((error as AxiosError).response?.status !== 200) {
          toast.info('Your token was expired')
          get().logout()
        }
      }
    }
    
  },
})

const useStore = create(
  devtools(
    persist(useUserStore, {
      name: 'user',
      getStorage: () => AsyncStorage,
    }),
  ),
)

export default useStore
