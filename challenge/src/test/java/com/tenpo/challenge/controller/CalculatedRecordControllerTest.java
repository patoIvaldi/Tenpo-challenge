package com.tenpo.challenge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tenpo.challenge.model.CalculatedRecord;
import com.tenpo.challenge.model.ThirdPartyPercentage;
import com.tenpo.challenge.service.CalculatedRecordServiceImpl;
import com.tenpo.challenge.service.ThirdPartyPercentageServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CalculatedRecordControllerTest {

    @InjectMocks
    private CalculatedRecordController calculatedRecordController;

    @Mock
    private CalculatedRecordServiceImpl calculatedRecordServiceImpl;

    @Mock
    private ThirdPartyPercentageServiceImpl thirdPartyPercentageServiceImpl;

    @Test
    public void testAllRecords() throws Exception {

        List<CalculatedRecord> records = new ArrayList<CalculatedRecord>();
        Page<CalculatedRecord> page = new PageImpl<CalculatedRecord>(records);
        Mockito.when(calculatedRecordServiceImpl.getAll(Mockito.any(Pageable.class))).thenReturn(page);

        Page<CalculatedRecord> result = calculatedRecordController.allRecords(Pageable.unpaged());

        Assertions.assertEquals(page, result);
    }

    @Test
    public void testCreateOrUpdateRecord() throws Exception {

        CalculatedRecord record = new CalculatedRecord();
        ThirdPartyPercentage thirdPartyPercentage = new ThirdPartyPercentage();
        Mockito.when(thirdPartyPercentageServiceImpl.getThirdPartyPercentage()).thenReturn(thirdPartyPercentage);
        Mockito.when(calculatedRecordServiceImpl.createOrUpdateRecord(Mockito.any(CalculatedRecord.class), Mockito.any(ThirdPartyPercentage.class)))
                .thenReturn(record);

        CalculatedRecord result = calculatedRecordController.createOrUpdateRecord(record);

        Assertions.assertEquals(record, result);
    }

    @Test
    public void testDeleteRecord() {

        Long id = 1L;

        ResponseEntity<String> result = calculatedRecordController.deleteRecord(id);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals("Registro borrado.", result.getBody());
        Mockito.verify(calculatedRecordServiceImpl).deleteById(id);
    }

    @Test
    public void testFindById() {

        Long id = 1L;
        CalculatedRecord record = new CalculatedRecord();
        Mockito.when(calculatedRecordServiceImpl.findById(Mockito.anyLong())).thenReturn(Optional.of(record));

        ResponseEntity<Optional<CalculatedRecord>> result = calculatedRecordController.findById(id);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(Optional.of(record), result.getBody());
    }

}
