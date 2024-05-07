export interface IBook{
    id:number
    title:string
    language:string,
    category:string,
    publisher:string,
    price:number
    numOfPages:number
    description:string
    quantity:number,
    image:string,
    authorName:string[]

}

export interface ICreateBook{
    title:string
    language:number,
    category:number,
    publisher:number,
    authors:number
    price:number
    numOfPages:number
    description:string
    quantity:number,
    image:string,
}

export interface IBookQuery{
    category:number|null
    language:number|null
    publisher:number|null
    min_price:number|null
    max_price:number|null
    sortByPrice:string
    sortByQuantity:string
    sortByNumOfPage:string
    search:string
}

export interface ICategory{
    id:number,
    categoryName:string
}

export interface ILanguage{
    id:number,
    languageName:string
}

export interface IPublisher{
    id:number
    publisherName:string
}

export interface IAuthor{
    id:number
    fullName:string
    birthDay:string
}