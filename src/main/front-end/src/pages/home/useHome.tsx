import React from "react";
import { Typography } from "@mui/material";
import { GridCellParams, GridColDef } from "@mui/x-data-grid";
import { IBook } from "types/IBook";
import { useNavigate } from "react-router-dom";
const useHome = () => {
    const navigate = useNavigate();
    const [currentPage, setCurrentPage] = React.useState(1)
    const [totalPage, setTotalPage] = React.useState(1)
    const [loading, setLoading] = React.useState(false)
    const columns: GridColDef[] = [
        {
            field:'id',
            headerName:'ID',
            width:370,
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
                <Typography>{params?.row?.image}</Typography>
            )
        },
        {
            field:'name',
            headerName:'Name',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.name}</Typography>
            )
        },
        {
            field:'author',
            headerName:'Author',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.author}</Typography>
            )
        },
        {
            field:'number',
            headerName:'Number',
            width:370,
            flex:1,
            renderCell:(params: GridCellParams) => (
                <Typography>{params?.row?.number}</Typography>
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
    ]
    const books: IBook[] = [
        {
            id: "1",
            image: "book1.jpg",
            name: "The Great Gatsby",
            author: "F. Scott Fitzgerald",
            number: "9781234567890",
            price: "$12.99"
        },
        {
            id: "2",
            image: "book2.jpg",
            name: "To Kill a Mockingbird",
            author: "Harper Lee",
            number: "9780061120084",
            price: "$10.49"
        },
        {
            id: "3",
            image: "book3.jpg",
            name: "1984",
            author: "George Orwell",
            number: "9780451524935",
            price: "$9.99"
        },
        {
            id: "4",
            image: "book4.jpg",
            name: "Pride and Prejudice",
            author: "Jane Austen",
            number: "9780141439518",
            price: "$8.99"
        },
        {
            id: "5",
            image: "book5.jpg",
            name: "The Catcher in the Rye",
            author: "J.D. Salinger",
            number: "9780316769488",
            price: "$11.25"
        },
        {
            id: "6",
            image: "book6.jpg",
            name: "Lord of the Flies",
            author: "William Golding",
            number: "9780571056866",
            price: "$7.99"
        },
        {
            id: "7",
            image: "book7.jpg",
            name: "Animal Farm",
            author: "George Orwell",
            number: "9780451526342",
            price: "$8.79"
        },
        {
            id: "8",
            image: "book8.jpg",
            name: "Brave New World",
            author: "Aldous Huxley",
            number: "9780060850524",
            price: "$10.99"
        },
        {
            id: "9",
            image: "book9.jpg",
            name: "The Hobbit",
            author: "J.R.R. Tolkien",
            number: "9780261102217",
            price: "$14.99"
        },
        {
            id: "10",
            image: "book10.jpg",
            name: "The Lord of the Rings",
            author: "J.R.R. Tolkien",
            number: "9780261102385",
            price: "$19.99"
        }
    ];
    const handleOnCellClick = (params: GridCellParams) =>{
        if(params.field === `id`){
            navigate(`book/${params.row.id}`)
        }
    }
    return {columns, books, loading, handleOnCellClick, totalPage, currentPage}
}
export default useHome;