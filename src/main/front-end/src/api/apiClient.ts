import { South } from '@mui/icons-material'
import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from 'axios'
import dayjs from 'dayjs'
import { jwtDecode } from 'jwt-decode'
import { toast } from 'react-toastify'
import userStore from 'stores/user'

const RequestMethod = {
  Get: 'GET',
  Post: 'POST',
  Put: 'PUT',
  Delete: 'DELETE',
  Options: 'OPTIONS',
  Head: 'HEAD',
  Patch: 'PATCH',
}

let refreshTokenRequest: Promise<void> | null = null

export default class APIClient {
  static async get(endpoint: string, params?: any, requestConfig?: AxiosRequestConfig) {
    const paramsConfig = params ? { params } : undefined

    return APIClient.request(
      {
        url: endpoint,
        method: RequestMethod.Get,
      },
      {
        ...paramsConfig,
        ...requestConfig,
      },
    )
  }

  static async post(endpoint: string, data?: any, requestConfig?: AxiosRequestConfig) {
    return APIClient.request(
      {
        url: endpoint,
        method: RequestMethod.Post,
      },
      {
        data,
        ...requestConfig,
      },
    )
  }

  static async put(endpoint: string, data?: any, requestConfig?: AxiosRequestConfig) {
    return APIClient.request(
      {
        url: endpoint,
        method: RequestMethod.Put,
      },
      {
        data,
        ...requestConfig,
      },
    )
  }

  static async delete(endpoint: string, requestConfig?: AxiosRequestConfig) {
    return APIClient.request(
      {
        url: endpoint,
        method: RequestMethod.Delete,
      },
      {
        ...requestConfig,
      },
    )
  }

  static async request(requestConfig: AxiosRequestConfig, config?: AxiosRequestConfig) {
    try {
      if(userStore.getState()?.user?.refreshToken && userStore.getState()?.user?.accessToken){
        const decodedRefreshToken = jwtDecode(userStore.getState()?.user?.refreshToken  as string);
        const decodeAcceassToken = jwtDecode(userStore.getState()?.user?.accessToken as string)
        if (userStore.getState()?.user) {
            const isExpiredRefreshToken = dayjs.unix(decodedRefreshToken.exp as number).diff(dayjs()) < 1
            if (isExpiredRefreshToken){
              refreshTokenRequest = refreshTokenRequest ? refreshTokenRequest : userStore.getState()?.refreshToken()
              await refreshTokenRequest
              refreshTokenRequest = null
              userStore.getState().logout()
              
            } 
            if (userStore.getState()?.user) {
              const isExpiredAccessToken = dayjs.unix(decodeAcceassToken.exp as number).diff(dayjs()) < 1
              if (isExpiredAccessToken) {
                refreshTokenRequest = refreshTokenRequest ? refreshTokenRequest : userStore.getState()?.refreshToken()
                await refreshTokenRequest
                refreshTokenRequest = null
              }
            }
          }
      }
     
      const axiosRequestConfig = {
        ...config,
        method: requestConfig.method,
        url: requestConfig.url,
        headers: {
          'Content-Type': config?.headers?.['Content-Type'] ? config.headers['Content-Type'] : 'application/json',
          Authorization: userStore.getState()?.user?.accessToken
            ? `${userStore.getState()?.user?.accessToken}`
            : '',
          ...config?.headers,
        },
      }

      const [axiosResponse] = await Promise.all([axios(axiosRequestConfig)])

      return {
        ...axiosResponse,
      }
    } catch (error) {
      if ((error as AxiosError).response) {
        if (
          (error as AxiosError).response?.status === 401 &&
          ((error as AxiosError).response?.data as { error_code: number })?.error_code === 40101
        ) {
          toast.info('Your account has been login from another device')
          userStore.getState().logout()
        }

        return (error as AxiosError).response as AxiosResponse<any, any>
      }

      toast.error('Internal Server Error')
      return {
        data: undefined,
        status: 500,
        statusText: 'Internal Server Error',
        headers: {},
        config: (error as AxiosError).request as any,
      }
    }
  }
}
