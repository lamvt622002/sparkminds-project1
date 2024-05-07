import { Box, Pagination } from "@mui/material"
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
    handleChangePage:(page:number) => void
}

const Table = ({columns, rows, loading, handleOnCellClick, totalPage, currentPage, handleChangePage}:TableProps) => {
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
            <Pagination
              count={totalPage}
              color="primary"
              className={styles.pagination}
              page={currentPage + 1}
              onChange={(event: React.ChangeEvent<unknown>, page: number) => {
                if (page !== currentPage && handleChangePage) handleChangePage(page-1)
              }}
            />       
        </Box>
        
    )
}
export default Table