import { Box } from "@mui/material"
import { DataGrid, GridCellParams, GridColDef } from '@mui/x-data-grid'
import { IBook } from "types/IBook"
import styles from "components/Table/Table.module.scss"
interface TableProps{
    columns:GridColDef[],
    rows:IBook[],
    loading:boolean,
    totalPage:number,
    currentPage:number,
    handleOnCellClick: (params: GridCellParams) => void
}

const Table = ({columns, rows, loading, handleOnCellClick, totalPage, currentPage}:TableProps) => {
    return(
        <Box className={styles.main}>
            <DataGrid
                className={styles.table}
                columns={columns}
                rows={rows}
                pageSizeOptions={[5, 10]}
                hideFooter
                disableColumnFilter
                disableColumnMenu
                rowSelection={false}
                loading={loading}
                onCellClick={handleOnCellClick}
                sx={{
                    '& .MuiDataGrid-virtualScroller::-webkit-scrollbar': {
                    WebkitAppearance: 'none',
                    width: '5px',
                    height: '5px',
                    },
                    '& .MuiDataGrid-virtualScroller::-webkit-scrollbar-thumb': {
                    borderRadius: '60px',
                    backgroundColor: '#bbb9c7',
                    boxShadow: '0 0 1px rgba(255, 255, 255, 0.5)',
                    },
                }}
            />       
        </Box>
        
    )
}
export default Table