import React from 'react';
import PropTypes from 'prop-types';
import MaterializePagination from '@material-ui/lab/Pagination';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import MenuItem from "@material-ui/core/MenuItem";
import Typography from "@material-ui/core/Typography";
import { ITEMS_PER_PAGE } from "./pagination.constants";
import { PaginationWrapper } from "./pagination.styled";

function Pagination(props) {
  const {
    itemsCount,
    limit,
    page,
    handlePageChange,
    handleLimitChange,
  } = props;

  return (
    <PaginationWrapper>
      <MaterializePagination
        display="inline"
        count={Math.floor(itemsCount / limit)}
        color="primary"
        shape="rounded"
        page={page}
        onChange={handlePageChange}
      />
      <FormControl variant="outlined" style={{ width: 100 }} size="small">
        <Select
          labelId="per-page-label"
          value={limit}
          onChange={handleLimitChange}
        >
          {ITEMS_PER_PAGE.map(
            value => <MenuItem key={value} value={value}>{value}</MenuItem>)}
        </Select>
      </FormControl>
      <Typography component="h5" variant="body2" display="inline">
        Per page
      </Typography>
    </PaginationWrapper>
  );
}

Pagination.propTypes = {
  itemsCount: PropTypes.number.isRequired,
  limit: PropTypes.number.isRequired,
  page: PropTypes.number.isRequired,
  handlePageChange: PropTypes.func.isRequired,
  handleLimitChange: PropTypes.func.isRequired,
};

export default Pagination;
