import React, { useState } from "react";
import PropTypes from "prop-types";
import { useMediaQuery } from '@material-ui/core';
import Pagination from "../pagination";
import { ITEMS_PER_PAGE } from "../pagination/pagination.constants";
import LargeTable from './components/large-table';
import SmallTable from './components/small-table';
import { TableWrapper } from "./responsive-table.styled";

function ResponsiveTable(props) {
  const {
    minWidthBreakPoint, columns,
    rows, usePagination, limit: limitProp,
  } = props;
  const [limit, setLimit] = useState(limitProp);
  const [offset, setOffset] = useState(0);
  const [page, setPage] = useState(1);
  const matches = useMediaQuery(`(min-width:${minWidthBreakPoint}px)`);

  if (rows.length > 0 && Array.isArray(rows[0]) &&
    rows.some(r => r.length !== columns.length)) {
    throw new Error(
      "Make sure row cells count is the same as columns count");
  }

  const handlePageChange = (event, value) => {
    const _offset = +value * limit;
    setPage(value);
    setOffset(_offset);
  };

  const handleLimitChange = (event) => { setLimit(event.target.value); };
  const paginatedRows = usePagination
    ? rows.slice(offset, offset + limit)
    : rows;
  const altProps = { ...props, rows: paginatedRows };
  const hasPagination = usePagination && rows.length > limit;

  return (
    <TableWrapper>
      {
        matches
          ? <LargeTable {...altProps}/>
          : <SmallTable {...altProps}/>
      }

      {
        hasPagination
        &&
        <Pagination
          page={page}
          itemsCount={rows.length}
          limit={limit}
          handleLimitChange={handleLimitChange}
          handlePageChange={handlePageChange}
        />
      }
    </TableWrapper>
  );
}

ResponsiveTable.defaultProps = {
  minWidthBreakPoint: 1150,
  usePagination: true,
  limit: ITEMS_PER_PAGE[0],
};

ResponsiveTable.propTypes = {
  minWidthBreakPoint: PropTypes.number,
  tableId: PropTypes.string.isRequired,
  columns: PropTypes.arrayOf(PropTypes.shape({
    name: PropTypes.node.isRequired,
    size: PropTypes.oneOfType([
      PropTypes.oneOf(["auto"]).isRequired,
      PropTypes.bool.isRequired,
      PropTypes.number.isRequired,
    ]),
  })).isRequired,
  rows: PropTypes.arrayOf(PropTypes.any).isRequired,
  noItemsNode: PropTypes.node.isRequired,
  usePagination: PropTypes.bool,
  limit: PropTypes.number,
};

export default ResponsiveTable;
