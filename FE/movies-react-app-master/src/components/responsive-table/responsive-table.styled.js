import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import styled from "styled-components";

export const GridRow = styled(Grid)(() => ({
  borderBottom: '1px solid rgba(224, 224, 224, 1)',
}));

export const GridCell = styled(Grid)(({ theme }) => ({
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'center',
  alignItems: 'flex-start',
  padding: 10,
  wordBreak: 'break-all',
  fontSize: 14,

  ['&.responsive-table-header']: {
    justifyContent: 'flex-start',
    alignItems: 'flex-start',
    color: 'rgba(0, 0, 0, 0.87)',
    fontWeight: 500,
    lineHeight: '1.5rem',
  },

  ['&.divider']: {
    backgroundColor: 'rgba(224, 224, 224, 1)',
  },
}));

export const TableWrapper = styled(Box)`
  border: 1px solid rgba(224,224,224,1);
`;
