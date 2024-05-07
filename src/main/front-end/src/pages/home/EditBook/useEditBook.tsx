import React from "react";
import { IAuthor, ICategory, ILanguage, IPublisher, IBook } from "types/IBook";
import { getAllCategory } from "api/category";
import { getAllLanguage } from "api/language";
import { getAllPublisher } from "api/publisher";
import { getAllAuthor } from "api/author";
import { toast } from "react-toastify";
import type { GetProp, UploadProps } from 'antd'
import { editBook, getBookById } from "api/book";
import { CONFIG } from "common/config";
import { useNavigate, useParams } from "react-router-dom";
type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0]
const useEditBook = () =>{
    const [fileUpload, setFileUpload] = React.useState<File | null>(null)
    const [disable, setDisable] = React.useState<boolean>(false);
    const [categories, setCategory] = React.useState<ICategory[]>([] as ICategory[])
    const [languages, setLanguages] = React.useState<ILanguage[]>([] as ILanguage[])
    const [publishers, setPublishers] = React.useState<IPublisher[]>([] as IPublisher[])
    const [authors, setAuthors] = React.useState<IAuthor[]>([] as IAuthor[])
    const [book, setBook] = React.useState<IBook>({} as IBook);
    const{bookId} = useParams()
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
    const handleGetBook = async() =>  {
        if(bookId){
            const res = await getBookById(bookId)
            if (res?.status === 200) {
                setBook(res?.data?.data)
                setFileUpload(null)
            }
        }
    }
    const removeImage = () => {
        setBook({
          ...book,
          image: '',
        })
        setFileUpload(null)
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
        setBook({
          ...book,
          image: URL.createObjectURL(file),
        })
        setFileUpload(file)
        return false
    }
      console.log(book);
    const handleEditBook = async () =>{
        if(!book?.title){
            toast.info("Please enter title")
            return
        }
        if(!book?.language){
            toast.info("Please enter language")
            return
        }
        if(!book?.category){
            toast.info("Please enter category")
            return
        }
        if(!book?.publisher){
            toast.info("Please enter publisher")
            return
        }
        if(!book?.authorName){
            toast.info("Please enter author")
            return
        }
        if(!book?.price){
            toast.info("Please enter price")
            return
        }
        if(!book?.numOfPages){
            toast.info("Please enter num of page")
            return
        }
        if(!book?.description){
            toast.info("Please enter description")
            return
        }
        if(!book?.quantity){
            toast.info("Please enter quatity")
            return
        }
      
        const formData = new FormData()
        const language = languages.find(language => language.languageName === book.language);
        if (!language) {
            toast.info('Language can not found')
            return
        }
        const category = categories.find(category => category.categoryName === book.category);
        if (!category) {
            toast.info('category can not found')
            return
        }
        const publisher = publishers.find(publisher => publisher.publisherName === book.publisher);
        if (!publisher) {
            toast.info('publisher can not found')
            return
        }
        const author = authors.find(author => author.fullName === book.authorName[0]);
        if (!author) {
            toast.info('author can not found')
            return
        }
        setDisable(true)
        formData.append("title", book.title)
        formData.append("languageId", language.id.toString());
        formData.append("categoryId", category.id.toString())
        formData.append("publisherId", publisher.id.toString())
        formData.append("authorId", author.id.toString())
        formData.append("price", book.price.toString())
        formData.append("numOfPages", book.numOfPages.toString())
        formData.append("description", book.description)
        formData.append("quantity", book.quantity.toString())
        if(fileUpload){
            formData.append("image", fileUpload)
        }

        const res = await toast.promise(editBook(formData, bookId as string), {pending:"Edit book ..."});
        if (res?.status === 204) {
            toast.success('Edit book success')
            navigate(`${CONFIG.PAGE_URLS.HOME}`)
          } else {
            if(Array.isArray(res?.data?.message)){
                toast.error(res?.data?.message[0])
            }
            toast.error(res?.data?.message)
          }
        setDisable(false)

    }


    React.useEffect(() => {
        handleGetAllCategory()
        handleGetAllBookLanguage()
        handleGetAllPublisher()
        handleGetAllAuthor()
        handleGetBook()
    },[])
    return {categories, languages, publishers,  authors, book, disable, setBook, beforeUpload, handleEditBook, removeImage}
}
export default useEditBook