export const getColumnName = (column) => column && column.name;

/**
 * @param {ResponsiveTableColumn} args
 * @returns {ResponsiveTableColumn}
 */
export const createColumn = ({
  name,
  xs,
  sm,
  md,
  lg,
  style = {},
}) => {

  return ({
    name,
    xs,
    sm,
    md,
    lg,
    style,
  });
};

/**
 * @param {ResponsiveTableColumn} column
 */
export const getSizesConfig = (column) => {
  return ({
    xs: column.xs,
    sm: column.sm,
    md: column.md,
    lg: column.lg,
  });
};
