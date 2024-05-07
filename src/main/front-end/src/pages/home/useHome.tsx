import React from "react";
import { Typography } from "@mui/material";
import { GridCellParams, GridColDef } from "@mui/x-data-grid";
import { IBook, IBookQuery, ICategory, ILanguage, IPublisher } from "types/IBook";
import { useNavigate } from "react-router-dom";
import { getAllBook, getBookByFilter } from "api/book";
import { Image } from 'antd'
import { getAllCategory } from "api/category";
import { getAllLanguage } from "api/language";
import { getAllPublisher } from "api/publisher";
const useHome = () => {
    const navigate = useNavigate();
    const [currentPage, setCurrentPage] = React.useState(0)
    const [totalPage, setTotalPage] = React.useState(10)
    const [loading, setLoading] = React.useState(false)
    const [books, setBooks] = React.useState<IBook[]>([] as IBook[])
    const [categories, setCategory] = React.useState<ICategory[]>([] as ICategory[])
    const [languages, setLanguages] = React.useState<ILanguage[]>([] as ILanguage[])
    const [publishers, setPublishers] = React.useState<IPublisher[]>([] as IPublisher[])
    const [query, setQuery] = React.useState<IBookQuery>({
        category:null,
        language:null,
        publisher:null,
        min_price:null,
        max_price:null,
        sortByPrice:'',
        sortByQuantity:'',
        sortByNumOfPage:'',
        search:''
    })
    const columns: GridColDef[] = [
        {
            field:'id',
            headerName:'ID',
            width:200,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.id}</Typography>
            )
        },
        {
            field:'image',
            headerName:'Image',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Image src={params?.row?.image} alt={params?.row?.image} />
            )
        },
        {
            field:'title',
            headerName:'Title',
            width:800,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.title}</Typography>
            )
        },
        {
            field:'language',
            headerName:'Language',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.language}</Typography>
            )
        },
        {
            field:'category',
            headerName:'Category',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.category}</Typography>
            )
        },
        {
            field:'publisher',
            headerName:'Publisher',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.publisher}</Typography>
            )
        },
        {
            field:'price',
            headerName:'Price',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.price}</Typography>
            )
        },
        {
            field:'numOfPages',
            headerName:'Nums of page',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.numOfPages}</Typography>
            )
        },  
        {
            field:'description',
            headerName:'Description',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.description}</Typography>
            )
        },
        {
            field:'quantity',
            headerName:'Quantity',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.quantity}</Typography>
            )
        },
        {
            field:'author',
            headerName:'Author',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.authorName}</Typography>
            )
        },
    ]
    const getAllBooks = async () => {
        const res = await getAllBook()

        if (res?.status === 200) {
            setBooks(res?.data?.data?.content)
            setCurrentPage(res?.data?.data?.number)
            setTotalPage(res?.data?.data?.totalPages)
        }
    }

    React.useEffect(() => {
        getAllBooks()
        handleGetAllCategory()
        handleGetAllBookLanguage()
        handleGetAllPublisher()
    },[])

    const handleOnCellClick = (params: GridCellParams) =>{
        if(params.field === `id`){
            navigate(`book/${params.row.id}`)
        }
    }

    const handleChangePage =async (page:number) => {
        const res = await getBookByFilter(query.category, query.language, query.publisher, query.min_price, query.max_price, query.sortByPrice, query.sortByQuantity, query.sortByNumOfPage, query.search,page)
        if (res?.status === 200) {
            setBooks(res?.data?.data?.content)
            setCurrentPage(res?.data?.data?.number)
            setTotalPage(res?.data?.data?.totalPages)
        }
    }

    const handleFilterBook = async() =>{
        const res = await getBookByFilter(query.category, query.language, query.publisher, query.min_price, query.max_price, query.sortByPrice, query.sortByQuantity, query.sortByNumOfPage ,query.search, )

        if (res?.status === 200) {
            setBooks(res?.data?.data?.content)
            setCurrentPage(res?.data?.data?.number)
            setTotalPage(res?.data?.data?.totalPages)
        }
    }
    const handleGetAllCategory = async() =>{
        const res = await getAllCategory()

        if (res?.status === 200) {
            setCategory(res?.data?.data)
        }
    }
    const handleGetAllBookLanguage = async() =>{
        const res = await getAllLanguage()

        if (res?.status === 200) {
            setLanguages(res?.data?.data)
        }
    }
    const handleGetAllPublisher = async() =>{
        const res = await getAllPublisher()

        if (res?.status === 200) {
            setPublishers(res?.data?.data)
        }
    }

    React.useEffect(() =>{
        handleFilterBook()
        console.log(query);
    },[query])

    return {columns, books, loading, handleOnCellClick, query, setQuery, categories, languages, publishers,handleChangePage, totalPage, currentPage}
}
export default useHome;