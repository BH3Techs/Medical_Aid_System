import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BankingDetails from './banking-details';
import BankingDetailsDetail from './banking-details-detail';
import BankingDetailsUpdate from './banking-details-update';
import BankingDetailsDeleteDialog from './banking-details-delete-dialog';

const BankingDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BankingDetails />} />
    <Route path="new" element={<BankingDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<BankingDetailsDetail />} />
      <Route path="edit" element={<BankingDetailsUpdate />} />
      <Route path="delete" element={<BankingDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BankingDetailsRoutes;
