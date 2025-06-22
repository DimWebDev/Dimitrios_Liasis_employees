import { DataGrid } from '@mui/x-data-grid';

const columns = [
  { field: 'id',       headerName: '#',         width: 70 },
  { field: 'emp1',     headerName: 'Employee ID #1', width: 150 },
  { field: 'emp2',     headerName: 'Employee ID #2', width: 150 },
  { field: 'projectId',headerName: 'Project ID',     width: 120 },
  { field: 'days',     headerName: 'Days worked',    width: 130 },
];

export default function ResultGrid({ rows }) {
  // DataGrid expects a unique 'id' per row
  const withIds = rows.map((r, idx) => ({ id: idx + 1, ...r }));

  return (
    <div style={{ height: 400, width: '100%' }}>
      <DataGrid
        rows={withIds}
        columns={columns}
        pageSize={10}
        rowsPerPageOptions={[10]}
        disableSelectionOnClick
      />
    </div>
  );
}
