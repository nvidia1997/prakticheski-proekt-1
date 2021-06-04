import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import Grid from "@material-ui/core/Grid";
import { GridCell, GridRow } from "../../responsive-table.styled";
import { getColumnName } from "../../responsive-table.utils";

function SmallTable(props) {
  const { tableId, columns, rows, noItemsNode } = props;
  
  const Row = ({ column, rowColumnContent }) => (
    <GridRow container item>
      <GridCell item xs={4}>
        {getColumnName(column)}
      </GridCell>
      
      <GridCell item xs={8}>
        {rowColumnContent}
      </GridCell>
    </GridRow>
  );
  
  const Table = () => {
    const NoResults = () => (
      <Grid item xs={12}>
        {noItemsNode}
      </Grid>
    );
    
    const DividerRow = ({ show }) => (
      show
        ?
        <GridRow container item>
          <GridCell className="divider" item xs={12}/>
        </GridRow>
        : null
    );
    
    return (
      <Grid
        container
        direction="row"
      >
        {
          rows && rows.length > 0
            ? rows.map((row, rowIndex) => (
              <Fragment key={`${tableId}-row-${rowIndex}`}>
                {
                  Array.isArray(row)
                    ? row.map((rowColumnContent, rowColumnIndex) => (
                      <Row
                        column={columns[rowColumnIndex]}
                        key={`${tableId}-row-column-${rowColumnIndex}`}
                        rowColumnContent={rowColumnContent}
                      />
                    ))
                    : <Row
                      column={columns[0]}
                      rowColumnContent={row}
                    />
                }
                
                <DividerRow show={rowIndex !== rows.length - 1}/>
              </Fragment>
            ))
            : <NoResults/>
        }
      </Grid>
    );
  };
  
  return (
    <Table/>
  );
}

SmallTable.propTypes = {
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
};

export default SmallTable;