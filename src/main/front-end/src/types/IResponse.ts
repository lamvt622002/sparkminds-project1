export interface IResponse {
    data: {
      data: any
      error_code: number
      message: string
      success: boolean
    }
    status: number
  }
 export const dataResponseDefault = {
    data: {
      data: [],
      error_code: 0,
      message: '',
      success: true,
    },
    status: 0,
  }