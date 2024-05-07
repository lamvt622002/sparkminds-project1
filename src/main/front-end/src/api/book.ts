import APIClient from "api/apiClient";
import {CONFIG} from "common/config";

const baseApi = process.env.REACT_APP_API_ENDPOINT

export const getAllBook = (page:number = 0, size:number= 10) => {
    return APIClient.get(baseApi + CONFIG.ENDPOINT_API.BOOK.GET_ALL_BOOK+`?page=${page}&size=${size}`)
}

export const getBookById = (bookId:string) => {
  return APIClient.get(baseApi + CONFIG.ENDPOINT_API.BOOK.GET_A_BOOK+bookId)
}

export const getBookByFilter = (
    category: number | null = null,
    language: number | null = null,
    publisher: number | null = null,
    min_price: number | null = null,
    max_price: number | null = null,
    sortByPrice:string|null = null,
    sortByQuantity:string|null = null,
    sortByNumOfPage:string|null=null,
    search: string | null = null,
    page: number = 0,
    size: number = 10
  ) => {
    const queryParams = new URLSearchParams({
      category: category !== null ? category.toString() : '',
      language: language !== null ? language.toString() : '',
      publisher: publisher !== null ? publisher.toString() : '',
      min_price: min_price !== null ? min_price.toString() : '',
      max_price: max_price !== null ? max_price.toString() : '',
      sortByPrice: sortByPrice !== null ? sortByPrice : '',
      sortByQuantity: sortByQuantity !== null ? sortByQuantity : '',
      sortByNumOfPage: sortByNumOfPage !== null ? sortByNumOfPage : '',
      search: search !== null ? search : '',
      page: page.toString(),
      size: size.toString()
    });
  
    const url = baseApi + CONFIG.ENDPOINT_API.BOOK.FILTER_BOOK + '?' + queryParams.toString();
  
    return APIClient.get(url);
  };
  


export const searchBook = (name:string, page:number, size:number) => {
    return APIClient.post(baseApi + CONFIG.ENDPOINT_API.BOOK.SEARCH_BOOK+`?name=${name}&page=${page}&size=${size}`)
}

export const createBook = (data:FormData) => {
  return APIClient.post(baseApi + CONFIG.ENDPOINT_API.BOOK.CREATE_BOOK, data, {
    headers:{
      'Content-Type': 'multipart/form-data',
    }
  })
}

export const editBook = (data:FormData, bookId:string) => {
  return APIClient.put(baseApi + CONFIG.ENDPOINT_API.BOOK.EDIT_BOOK+bookId, data, {
    headers:{
      'Content-Type': 'multipart/form-data',
    }
  })
}


