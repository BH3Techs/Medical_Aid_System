import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ServiceProvider from './service-provider';
import ServiceProviderDetail from './service-provider-detail';
import ServiceProviderUpdate from './service-provider-update';
import ServiceProviderDeleteDialog from './service-provider-delete-dialog';

const ServiceProviderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ServiceProvider />} />
    <Route path="new" element={<ServiceProviderUpdate />} />
    <Route path=":id">
      <Route index element={<ServiceProviderDetail />} />
      <Route path="edit" element={<ServiceProviderUpdate />} />
      <Route path="delete" element={<ServiceProviderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServiceProviderRoutes;
