import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContactDetails } from 'app/shared/model/contact-details.model';
import { getEntities as getContactDetails } from 'app/entities/contact-details/contact-details.reducer';
import { IServiceProvider } from 'app/shared/model/service-provider.model';
import { getEntity, updateEntity, createEntity, reset } from './service-provider.reducer';

export const ServiceProviderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contactDetails = useAppSelector(state => state.contactDetails.entities);
  const serviceProviderEntity = useAppSelector(state => state.serviceProvider.entity);
  const loading = useAppSelector(state => state.serviceProvider.loading);
  const updating = useAppSelector(state => state.serviceProvider.updating);
  const updateSuccess = useAppSelector(state => state.serviceProvider.updateSuccess);

  const handleClose = () => {
    navigate('/service-provider');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getContactDetails({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...serviceProviderEntity,
      ...values,
      contactDetails: contactDetails.find(it => it.id.toString() === values.contactDetails.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...serviceProviderEntity,
          contactDetails: serviceProviderEntity?.contactDetails?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.serviceProvider.home.createOrEditLabel" data-cy="ServiceProviderCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.serviceProvider.home.createOrEditLabel">Create or edit a ServiceProvider</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="service-provider-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.serviceProvider.name')}
                id="service-provider-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.serviceProvider.aHFOZNumber')}
                id="service-provider-aHFOZNumber"
                name="aHFOZNumber"
                data-cy="aHFOZNumber"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.serviceProvider.description')}
                id="service-provider-description"
                name="description"
                data-cy="description"
                check
                type="checkbox"
              />
              <ValidatedField
                id="service-provider-contactDetails"
                name="contactDetails"
                data-cy="contactDetails"
                label={translate('medicalAidSystemApp.serviceProvider.contactDetails')}
                type="select"
              >
                <option value="" key="0" />
                {contactDetails
                  ? contactDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/service-provider" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ServiceProviderUpdate;
