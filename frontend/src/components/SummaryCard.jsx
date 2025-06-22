import { Card, CardContent, Typography } from '@mui/material';

export default function SummaryCard({ topPair, ...props }) {
  const { emp1, emp2, totalDays } = topPair;
  return (
    <Card {...props}>
      <CardContent>
        <Typography variant="h6">
          Top Pair: <strong>{emp1}</strong> &amp; <strong>{emp2}</strong>
        </Typography>
        <Typography variant="body1">
          Total days worked together: <strong>{totalDays}</strong>
        </Typography>
      </CardContent>
    </Card>
  );
}
