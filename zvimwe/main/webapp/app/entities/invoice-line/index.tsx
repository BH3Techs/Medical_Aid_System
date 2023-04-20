import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InvoiceLine from './invoice-line';
import InvoiceLineDetail from './invoice-line-detail';
import InvoiceLineUpdate from './invoice-line-update';
import InvoiceLineDeleteDialog from './invoice-line-delete-dialog';

const InvoiceLineRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InvoiceLine />} />
    <Route path="new" element={<InvoiceLineUpdate />} />
    <Route path=":id">
      <Route index element={<InvoiceLineDetail />} />
      <Route path="edit" element={<InvoiceLineUpdate />} />
      <Route path="delete" element={<InvoiceLineDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InvoiceLineRoutes;
