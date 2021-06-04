import Grid from "@material-ui/core/Grid";
import PropTypes from 'prop-types';
import React from 'react';
import { GridCell, GridRow } from "../../responsive-table.styled";
import { getColumnName, getSizesConfig } from "../../responsive-table.utils";

function LargeTable(props) {
  const { tableId, columns, rows, noItemsNode } = props;

  const Header = () => (
    <GridRow container item>
      {
        columns.map((column, columnIndex) => (
          <GridCell
            className="responsive-table-header"
            item
            key={`${tableId}-header-cell-${columnIndex}`}
            style={column?.style}
            {...getSizesConfig(column)}
          >
            {getColumnName(column)}
          </GridCell>
        ))
      }
    </GridRow>
  );

  const Body = () => {
    const NoResults = () => (
      <GridRow
        container
        item
      >
        <GridCell
          item
          mt={4}
          xs={12}
        >
          {noItemsNode}
        </GridCell>
      </GridRow>
    );

    const Rows = () => (
      rows.map(((row, rowIndex) =>
          (
            <GridRow
              container
              item
              key={`${tableId}-row-${rowIndex}`}
            >
              {
                Array.isArray(row)
                  ? row.map((rowColumnContent, rowColumnIndex) => (
                    <GridCell
                      item
                      key={`${tableId}-body-column-${rowColumnIndex}`}
                      {...getSizesConfig(columns[rowColumnIndex])}
                    >
                      {rowColumnContent}
                    </GridCell>
                  ))
                  : <GridCell
                    item
                    xs
                  >
                    {row}
                  </GridCell>
              }
            </GridRow>
          )
      ))
    );

    return (
      rows && rows.length > 0 ? <Rows/> : <NoResults/>
    );
  };

  return (
    <Grid container>
      <Header/>
      <Body/>
    </Grid>
  );
}

LargeTable.propTypes = {
  tableId: PropTypes.string.isRequired,
  columns: PropTypes.arrayOf(PropTypes.shape({
    name: PropTypes.any.isRequired,
    xs: PropTypes.oneOfType([
      PropTypes.oneOf(["auto"]).isRequired,
      PropTypes.bool.isRequired,
      PropTypes.number.isRequired,
    ]),
    sm: PropTypes.oneOfType([
      PropTypes.oneOf(["auto"]).isRequired,
      PropTypes.bool.isRequired,
      PropTypes.number.isRequired,
    ]),
    md: PropTypes.oneOfType([
      PropTypes.oneOf(["auto"]).isRequired,
      PropTypes.bool.isRequired,
      PropTypes.number.isRequired,
    ]),
    lg: PropTypes.oneOfType([
      PropTypes.oneOf(["auto"]).isRequired,
      PropTypes.bool.isRequired,
      PropTypes.number.isRequired,
    ]),
  })).isRequired,
  rows: PropTypes.arrayOf(PropTypes.any).isRequired,
  noItemsNode: PropTypes.node.isRequired,
};

export default LargeTable;
