import React from "react";
import { IAuthor, ICreateBook, ICategory, ILanguage, IPublisher } from "types/IBook";
import { getAllCategory } from "api/category";
import { getAllLanguage } from "api/language";
import { getAllPublisher } from "api/publisher";
import { getAllAuthor } from "api/author";
import { toast } from "react-toastify";
import type { GetProp, UploadProps } from 'antd'
import { createBook } from "api/book";
import { CONFIG } from "common/config";
import { useNavigate } from "react-router-dom";
type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0]
const useCreateBook = () =>{
    const [fileUpload, setFileUpload] = React.useState<File | null>(null)
    const [disable, setDisable] = React.useState<boolean>(false);
    const [categories, setCategory] = React.useState<ICategory[]>([] as ICategory[])
    const [languages, setLanguages] = React.useState<ILanguage[]>([] as ILanguage[])
    const [publishers, setPublishers] = React.useState<IPublisher[]>([] as IPublisher[])
    const [authors, setAuthors] = React.useState<IAuthor[]>([] as IAuthor[])
    const [dataBook, setDataBook] = React.useState<ICreateBook> (
    {
        title:'',
        language:0,
        category:0,
        publisher:0,
        authors:0,
        price:0,
        numOfPages:0,
        description:'',
        quantity:0,
        image:'',
    })
    const navigate = useNavigate();
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
    const handleGetAllAuthor = async() =>{
        const res = await getAllAuthor()

        if (res?.status === 200) {
            setAuthors(res?.data?.data)
        }
    }
    const beforeUpload = (file: FileType) => {
        const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
        if (!isJpgOrPng) {
          toast.info('You can only upload JPG/PNG file!')
          return false
        }
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isLt2M) {
          toast.info('Image must smaller than 2MB!')
          return false
        }
        setDataBook({
          ...dataBook,
          image: URL.createObjectURL(file),
        })
        setFileUpload(file)
        return false
      }

    const handleCreateBook = async () =>{
        if(!dataBook?.title){
            toast.info("Please enter title")
            return
        }
        if(!dataBook?.language){
            toast.info("Please enter language")
            return
        }
        if(!dataBook?.category){
            toast.info("Please enter category")
            return
        }
        if(!dataBook?.publisher){
            toast.info("Please enter publisher")
            return
        }
        if(!dataBook?.authors){
            toast.info("Please enter author")
            return
        }
        if(!dataBook?.price){
            toast.info("Please enter price")
            return
        }
        if(!dataBook?.numOfPages){
            toast.info("Please enter num of page")
            return
        }
        if(!dataBook?.description){
            toast.info("Please enter description")
            return
        }
        if(!dataBook?.quantity){
            toast.info("Please enter quatity")
            return
        }
        if (!fileUpload) {
            toast.info('Please upload image')
            return
          }
        setDisable(true)
        
        const formData = new FormData()
        formData.append("title", dataBook.title)
        formData.append("languageId", dataBook.language.toString())
        formData.append("categoryId", dataBook.category.toString())
        formData.append("publisherId", dataBook.publisher.toString())
        formData.append("authorId", dataBook.authors.toString())
        formData.append("price", dataBook.price.toString())
        formData.append("numOfPages", dataBook.numOfPages.toString())
        formData.append("description", dataBook.description)
        formData.append("quantity", dataBook.quantity.toString())
        formData.append("image", fileUpload)

        const res = await toast.promise(createBook(formData), {pending:"Create book ..."});
        if (res?.status === 204) {
            toast.success('Create book success')
            navigate(`${CONFIG.PAGE_URLS.HOME}`)
          } else {
            toast.error(res?.data?.message)
          }
          setDisable(false)

    }

    React.useEffect(() => {
        handleGetAllCategory()
        handleGetAllBookLanguage()
        handleGetAllPublisher()
        handleGetAllAuthor()
    },[])
    return {categories, languages, publishers,  authors, dataBook, disable, setDataBook, beforeUpload, handleCreateBook}
}
export default useCreateBook