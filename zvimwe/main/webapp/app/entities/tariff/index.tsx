import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tariff from './tariff';
import TariffDetail from './tariff-detail';
import TariffUpdate from './tariff-update';
import TariffDeleteDialog from './tariff-delete-dialog';

const TariffRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tariff />} />
    <Route path="new" element={<TariffUpdate />} />
    <Route path=":id">
      <Route index element={<TariffDetail />} />
      <Route path="edit" element={<TariffUpdate />} />
      <Route path="delete" element={<TariffDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TariffRoutes;
