import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPolicy } from 'app/shared/model/policy.model';
import { getEntities as getPolicies } from 'app/entities/policy/policy.reducer';
import { IContactDetails } from 'app/shared/model/contact-details.model';
import { getEntities as getContactDetails } from 'app/entities/contact-details/contact-details.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';
import { getEntity, updateEntity, createEntity, reset } from './invoice.reducer';

export const InvoiceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const policies = useAppSelector(state => state.policy.entities);
  const contactDetails = useAppSelector(state => state.contactDetails.entities);
  const invoiceEntity = useAppSelector(state => state.invoice.entity);
  const loading = useAppSelector(state => state.invoice.loading);
  const updating = useAppSelector(state => state.invoice.updating);
  const updateSuccess = useAppSelector(state => state.invoice.updateSuccess);
  const invoiceStatusValues = Object.keys(InvoiceStatus);

  const handleClose = () => {
    navigate('/invoice');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPolicies({}));
    dispatch(getContactDetails({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.invoiceDate = convertDateTimeToServer(values.invoiceDate);

    const entity = {
      ...invoiceEntity,
      ...values,
      policy: policies.find(it => it.id.toString() === values.policy.toString()),
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
      ? {
          invoiceDate: displayDefaultDateTime(),
        }
      : {
          invoiceStatus: 'OPEN',
          ...invoiceEntity,
          invoiceDate: convertDateTimeFromServer(invoiceEntity.invoiceDate),
          policy: invoiceEntity?.policy?.id,
          contactDetails: invoiceEntity?.contactDetails?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.invoice.home.createOrEditLabel" data-cy="InvoiceCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.invoice.home.createOrEditLabel">Create or edit a Invoice</Translate>
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
                  id="invoice-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.invoice.invoiceNumber')}
                id="invoice-invoiceNumber"
                name="invoiceNumber"
                data-cy="invoiceNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.invoice.invoiceStatus')}
                id="invoice-invoiceStatus"
                name="invoiceStatus"
                data-cy="invoiceStatus"
                type="select"
              >
                {invoiceStatusValues.map(invoiceStatus => (
                  <option value={invoiceStatus} key={invoiceStatus}>
                    {translate('medicalAidSystemApp.InvoiceStatus.' + invoiceStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('medicalAidSystemApp.invoice.amountPayable')}
                id="invoice-amountPayable"
                name="amountPayable"
                data-cy="amountPayable"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.invoice.invoiceDate')}
                id="invoice-invoiceDate"
                name="invoiceDate"
                data-cy="invoiceDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.invoice.nextInvoiceDate')}
                id="invoice-nextInvoiceDate"
                name="nextInvoiceDate"
                data-cy="nextInvoiceDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.invoice.invoiceAmount')}
                id="invoice-invoiceAmount"
                name="invoiceAmount"
                data-cy="invoiceAmount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.invoice.expectedPaymentDate')}
                id="invoice-expectedPaymentDate"
                name="expectedPaymentDate"
                data-cy="expectedPaymentDate"
                type="date"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.invoice.gracePeriod')}
                id="invoice-gracePeriod"
                name="gracePeriod"
                data-cy="gracePeriod"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="invoice-policy"
                name="policy"
                data-cy="policy"
                label={translate('medicalAidSystemApp.invoice.policy')}
                type="select"
              >
                <option value="" key="0" />
                {policies
                  ? policies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="invoice-contactDetails"
                name="contactDetails"
                data-cy="contactDetails"
                label={translate('medicalAidSystemApp.invoice.contactDetails')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/invoice" replace color="info">
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

export default InvoiceUpdate;
