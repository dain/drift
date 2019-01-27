/*
 * Copyright (C) 2012 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.airlift.drift.integration.scribe.apache;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings(value = {"ALL", "all"})
public class scribe
{
    public interface Iface
    {
        ResultCode Log(List<LogEntry> messages)
                throws TException;
    }

    public interface AsyncIface
    {
        void Log(List<LogEntry> messages, org.apache.thrift.async.AsyncMethodCallback<ResultCode> resultHandler)
                throws TException;
    }

    public static class Client
            extends org.apache.thrift.TServiceClient
            implements Iface
    {
        public static class Factory
                implements org.apache.thrift.TServiceClientFactory<Client>
        {
            public Factory() {}

            public Client getClient(org.apache.thrift.protocol.TProtocol prot)
            {
                return new Client(prot);
            }

            public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot)
            {
                return new Client(iprot, oprot);
            }
        }

        public Client(org.apache.thrift.protocol.TProtocol prot)
        {
            super(prot, prot);
        }

        public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot)
        {
            super(iprot, oprot);
        }

        public ResultCode Log(List<LogEntry> messages)
                throws TException
        {
            send_Log(messages);
            return recv_Log();
        }

        public void send_Log(List<LogEntry> messages)
                throws TException
        {
            Log_args args = new Log_args();
            args.setMessages(messages);
            sendBase("Log", args);
        }

        public ResultCode recv_Log()
                throws TException
        {
            Log_result result = new Log_result();
            receiveBase(result, "Log");
            if (result.isSetSuccess()) {
                return result.success;
            }
            throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "Log failed: unknown result");
        }
    }

    public static class AsyncClient
            extends org.apache.thrift.async.TAsyncClient
            implements AsyncIface
    {
        public static class Factory
                implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient>
        {
            private org.apache.thrift.async.TAsyncClientManager clientManager;
            private org.apache.thrift.protocol.TProtocolFactory protocolFactory;

            public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory)
            {
                this.clientManager = clientManager;
                this.protocolFactory = protocolFactory;
            }

            public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport)
            {
                return new AsyncClient(protocolFactory, clientManager, transport);
            }
        }

        public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory,
                org.apache.thrift.async.TAsyncClientManager clientManager,
                org.apache.thrift.transport.TNonblockingTransport transport)
        {
            super(protocolFactory, clientManager, transport);
        }

        public void Log(List<LogEntry> messages, org.apache.thrift.async.AsyncMethodCallback<ResultCode> resultHandler)
                throws TException
        {
            checkReady();
            Log_call method_call = new Log_call(messages, resultHandler, this, ___protocolFactory, ___transport);
            this.___currentMethod = method_call;
            ___manager.call(method_call);
        }

        public static class Log_call
                extends org.apache.thrift.async.TAsyncMethodCall<ResultCode>
        {
            private List<LogEntry> messages;

            public Log_call(List<LogEntry> messages,
                    org.apache.thrift.async.AsyncMethodCallback<ResultCode> resultHandler,
                    org.apache.thrift.async.TAsyncClient client,
                    org.apache.thrift.protocol.TProtocolFactory protocolFactory,
                    org.apache.thrift.transport.TNonblockingTransport transport)
                    throws TException
            {
                super(client, protocolFactory, transport, resultHandler, false);
                this.messages = messages;
            }

            public void write_args(org.apache.thrift.protocol.TProtocol prot)
                    throws TException
            {
                prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("Log", org.apache.thrift.protocol.TMessageType.CALL, 0));
                Log_args args = new Log_args();
                args.setMessages(messages);
                args.write(prot);
                prot.writeMessageEnd();
            }

            public ResultCode getResult()
                    throws TException
            {
                if (getState() != State.RESPONSE_READ) {
                    throw new IllegalStateException("Method call not finished!");
                }
                org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
                org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
                return (new Client(prot)).recv_Log();
            }
        }
    }

    public static class Processor<I extends Iface>
            extends org.apache.thrift.TBaseProcessor<I>
            implements org.apache.thrift.TProcessor
    {
        public Processor(I iface)
        {
            super(iface, getProcessMap(new HashMap<>()));
        }

        protected Processor(I iface, Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap)
        {
            super(iface, getProcessMap(processMap));
        }

        private static <I extends Iface> Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> getProcessMap(Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap)
        {
            processMap.put("Log", new Log());
            return processMap;
        }

        public static class Log<I extends Iface>
                extends org.apache.thrift.ProcessFunction<I, Log_args>
        {
            public Log()
            {
                super("Log");
            }

            public Log_args getEmptyArgsInstance()
            {
                return new Log_args();
            }

            protected boolean isOneway()
            {
                return false;
            }

            protected boolean handleRuntimeExceptions()
            {
                return false;
            }

            public Log_result getResult(I iface, Log_args args)
                    throws TException
            {
                Log_result result = new Log_result();
                result.success = iface.Log(args.messages);
                return result;
            }
        }
    }

    public static class Log_args
            implements org.apache.thrift.TBase<Log_args, Log_args._Fields>, java.io.Serializable, Cloneable, Comparable<Log_args>
    {
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Log_args");

        private static final org.apache.thrift.protocol.TField MESSAGES_FIELD_DESC = new org.apache.thrift.protocol.TField(
                "messages",
                org.apache.thrift.protocol.TType.LIST,
                (short) 1);

        private static final SchemeFactory STANDARD_SCHEME_FACTORY = new Log_argsStandardSchemeFactory();
        private static final SchemeFactory TUPLE_SCHEME_FACTORY = new Log_argsTupleSchemeFactory();

        public List<LogEntry> messages; // required

        /**
         * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
         */
        public enum _Fields
                implements org.apache.thrift.TFieldIdEnum
        {
            MESSAGES((short) 1, "messages");

            private static final Map<String, _Fields> byName = new HashMap<>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, or null if its not found.
             */
            public static _Fields findByThriftId(int fieldId)
            {
                switch (fieldId) {
                    case 1: // MESSAGES
                        return MESSAGES;
                    default:
                        return null;
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, throwing an exception
             * if it is not found.
             */
            public static _Fields findByThriftIdOrThrow(int fieldId)
            {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) {
                    throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                }
                return fields;
            }

            /**
             * Find the _Fields constant that matches name, or null if its not found.
             */
            public static _Fields findByName(String name)
            {
                return byName.get(name);
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName)
            {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            public short getThriftFieldId()
            {
                return _thriftId;
            }

            public String getFieldName()
            {
                return _fieldName;
            }
        }

        // isset id assignments
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<>(_Fields.class);
            tmpMap.put(_Fields.MESSAGES, new org.apache.thrift.meta_data.FieldMetaData("messages", org.apache.thrift.TFieldRequirementType.DEFAULT,
                    new org.apache.thrift.meta_data.ListMetaData(
                            org.apache.thrift.protocol.TType.LIST,
                            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, LogEntry.class))));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Log_args.class, metaDataMap);
        }

        public Log_args()
        {
        }

        public Log_args(
                List<LogEntry> messages)
        {
            this();
            this.messages = messages;
        }

        /**
         * Performs a deep copy on <i>other</i>.
         */
        public Log_args(Log_args other)
        {
            if (other.isSetMessages()) {
                List<LogEntry> __this__messages = new ArrayList<LogEntry>(other.messages.size());
                for (LogEntry other_element : other.messages) {
                    __this__messages.add(new LogEntry(other_element));
                }
                this.messages = __this__messages;
            }
        }

        public Log_args deepCopy()
        {
            return new Log_args(this);
        }

        @Override
        public void clear()
        {
            this.messages = null;
        }

        public int getMessagesSize()
        {
            return (this.messages == null) ? 0 : this.messages.size();
        }

        public Iterator<LogEntry> getMessagesIterator()
        {
            return (this.messages == null) ? null : this.messages.iterator();
        }

        public void addToMessages(LogEntry elem)
        {
            if (this.messages == null) {
                this.messages = new ArrayList<LogEntry>();
            }
            this.messages.add(elem);
        }

        public List<LogEntry> getMessages()
        {
            return this.messages;
        }

        public Log_args setMessages(List<LogEntry> messages)
        {
            this.messages = messages;
            return this;
        }

        public void unsetMessages()
        {
            this.messages = null;
        }

        /**
         * Returns true if field messages is set (has been assigned a value) and false otherwise
         */
        public boolean isSetMessages()
        {
            return this.messages != null;
        }

        public void setMessagesIsSet(boolean value)
        {
            if (!value) {
                this.messages = null;
            }
        }

        public void setFieldValue(_Fields field, Object value)
        {
            switch (field) {
                case MESSAGES:
                    if (value == null) {
                        unsetMessages();
                    }
                    else {
                        setMessages((List<LogEntry>) value);
                    }
                    break;
            }
        }

        public Object getFieldValue(_Fields field)
        {
            switch (field) {
                case MESSAGES:
                    return getMessages();
            }
            throw new IllegalStateException();
        }

        /**
         * Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise
         */
        public boolean isSet(_Fields field)
        {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case MESSAGES:
                    return isSetMessages();
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean equals(Object that)
        {
            if (that == null) {
                return false;
            }
            if (that instanceof Log_args) {
                return this.equals((Log_args) that);
            }
            return false;
        }

        public boolean equals(Log_args that)
        {
            if (that == null) {
                return false;
            }
            if (this == that) {
                return true;
            }

            boolean this_present_messages = true && this.isSetMessages();
            boolean that_present_messages = true && that.isSetMessages();
            if (this_present_messages || that_present_messages) {
                if (!(this_present_messages && that_present_messages)) {
                    return false;
                }
                if (!this.messages.equals(that.messages)) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public int hashCode()
        {
            int hashCode = 1;

            hashCode = hashCode * 8191 + ((isSetMessages()) ? 131071 : 524287);
            if (isSetMessages()) {
                hashCode = hashCode * 8191 + messages.hashCode();
            }

            return hashCode;
        }

        @Override
        public int compareTo(Log_args other)
        {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;

            lastComparison = Boolean.valueOf(isSetMessages()).compareTo(other.isSetMessages());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetMessages()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.messages, other.messages);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId)
        {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot)
                throws TException
        {
            scheme(iprot).read(iprot, this);
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot)
                throws TException
        {
            scheme(oprot).write(oprot, this);
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder("Log_args(");
            boolean first = true;

            sb.append("messages:");
            if (this.messages == null) {
                sb.append("null");
            }
            else {
                sb.append(this.messages);
            }
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate()
                throws TException
        {
            // check for required fields
            // check for sub-struct validity
        }

        private void writeObject(java.io.ObjectOutputStream out)
                throws java.io.IOException
        {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            }
            catch (TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in)
                throws java.io.IOException, ClassNotFoundException
        {
            try {
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            }
            catch (TException te) {
                throw new java.io.IOException(te);
            }
        }

        private static class Log_argsStandardSchemeFactory
                implements SchemeFactory
        {
            public Log_argsStandardScheme getScheme()
            {
                return new Log_argsStandardScheme();
            }
        }

        private static class Log_argsStandardScheme
                extends StandardScheme<Log_args>
        {
            public void read(org.apache.thrift.protocol.TProtocol iprot, Log_args struct)
                    throws TException
            {
                org.apache.thrift.protocol.TField schemeField;
                iprot.readStructBegin();
                while (true) {
                    schemeField = iprot.readFieldBegin();
                    if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                        break;
                    }
                    switch (schemeField.id) {
                        case 1: // MESSAGES
                            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
                                {
                                    org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                                    struct.messages = new ArrayList<>(_list0.size);
                                    LogEntry _elem1;
                                    for (int _i2 = 0; _i2 < _list0.size; ++_i2) {
                                        _elem1 = new LogEntry();
                                        _elem1.read(iprot);
                                        struct.messages.add(_elem1);
                                    }
                                    iprot.readListEnd();
                                }
                                struct.setMessagesIsSet(true);
                            }
                            else {
                                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                            }
                            break;
                        default:
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    iprot.readFieldEnd();
                }
                iprot.readStructEnd();

                // check for required fields of primitive type, which can't be checked in the validate method
                struct.validate();
            }

            public void write(org.apache.thrift.protocol.TProtocol oprot, Log_args struct)
                    throws TException
            {
                struct.validate();

                oprot.writeStructBegin(STRUCT_DESC);
                if (struct.messages != null) {
                    oprot.writeFieldBegin(MESSAGES_FIELD_DESC);
                    {
                        oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.messages.size()));
                        for (LogEntry _iter3 : struct.messages) {
                            _iter3.write(oprot);
                        }
                        oprot.writeListEnd();
                    }
                    oprot.writeFieldEnd();
                }
                oprot.writeFieldStop();
                oprot.writeStructEnd();
            }
        }

        private static class Log_argsTupleSchemeFactory
                implements SchemeFactory
        {
            public Log_argsTupleScheme getScheme()
            {
                return new Log_argsTupleScheme();
            }
        }

        private static class Log_argsTupleScheme
                extends TupleScheme<Log_args>
        {
            @Override
            public void write(org.apache.thrift.protocol.TProtocol prot, Log_args struct)
                    throws TException
            {
                TTupleProtocol oprot = (TTupleProtocol) prot;
                BitSet optionals = new BitSet();
                if (struct.isSetMessages()) {
                    optionals.set(0);
                }
                oprot.writeBitSet(optionals, 1);
                if (struct.isSetMessages()) {
                    {
                        oprot.writeI32(struct.messages.size());
                        for (LogEntry _iter4 : struct.messages) {
                            _iter4.write(oprot);
                        }
                    }
                }
            }

            @Override
            public void read(org.apache.thrift.protocol.TProtocol prot, Log_args struct)
                    throws TException
            {
                TTupleProtocol iprot = (TTupleProtocol) prot;
                BitSet incoming = iprot.readBitSet(1);
                if (incoming.get(0)) {
                    {
                        org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
                        struct.messages = new ArrayList<>(_list5.size);
                        LogEntry _elem6;
                        for (int _i7 = 0; _i7 < _list5.size; ++_i7) {
                            _elem6 = new LogEntry();
                            _elem6.read(iprot);
                            struct.messages.add(_elem6);
                        }
                    }
                    struct.setMessagesIsSet(true);
                }
            }
        }

        private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto)
        {
            return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
        }
    }

    public static class Log_result
            implements org.apache.thrift.TBase<Log_result, Log_result._Fields>, java.io.Serializable, Cloneable, Comparable<Log_result>
    {
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Log_result");

        private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField(
                "success",
                org.apache.thrift.protocol.TType.I32,
                (short) 0);

        private static final SchemeFactory STANDARD_SCHEME_FACTORY = new Log_resultStandardSchemeFactory();
        private static final SchemeFactory TUPLE_SCHEME_FACTORY = new Log_resultTupleSchemeFactory();

        /**
         * @see ResultCode
         */
        public ResultCode success; // required

        /**
         * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
         */
        public enum _Fields
                implements org.apache.thrift.TFieldIdEnum
        {
            /**
             * @see ResultCode
             */
            SUCCESS((short) 0, "success");

            private static final Map<String, _Fields> byName = new HashMap<>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, or null if its not found.
             */
            public static _Fields findByThriftId(int fieldId)
            {
                switch (fieldId) {
                    case 0: // SUCCESS
                        return SUCCESS;
                    default:
                        return null;
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, throwing an exception
             * if it is not found.
             */
            public static _Fields findByThriftIdOrThrow(int fieldId)
            {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) {
                    throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                }
                return fields;
            }

            /**
             * Find the _Fields constant that matches name, or null if its not found.
             */
            public static _Fields findByName(String name)
            {
                return byName.get(name);
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName)
            {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            public short getThriftFieldId()
            {
                return _thriftId;
            }

            public String getFieldName()
            {
                return _fieldName;
            }
        }

        // isset id assignments
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<>(_Fields.class);
            tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT,
                    new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, ResultCode.class)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Log_result.class, metaDataMap);
        }

        public Log_result()
        {
        }

        public Log_result(
                ResultCode success)
        {
            this();
            this.success = success;
        }

        /**
         * Performs a deep copy on <i>other</i>.
         */
        public Log_result(Log_result other)
        {
            if (other.isSetSuccess()) {
                this.success = other.success;
            }
        }

        public Log_result deepCopy()
        {
            return new Log_result(this);
        }

        @Override
        public void clear()
        {
            this.success = null;
        }

        /**
         * @see ResultCode
         */
        public ResultCode getSuccess()
        {
            return this.success;
        }

        /**
         * @see ResultCode
         */
        public Log_result setSuccess(ResultCode success)
        {
            this.success = success;
            return this;
        }

        public void unsetSuccess()
        {
            this.success = null;
        }

        /**
         * Returns true if field success is set (has been assigned a value) and false otherwise
         */
        public boolean isSetSuccess()
        {
            return this.success != null;
        }

        public void setSuccessIsSet(boolean value)
        {
            if (!value) {
                this.success = null;
            }
        }

        public void setFieldValue(_Fields field, Object value)
        {
            switch (field) {
                case SUCCESS:
                    if (value == null) {
                        unsetSuccess();
                    }
                    else {
                        setSuccess((ResultCode) value);
                    }
                    break;
            }
        }

        public Object getFieldValue(_Fields field)
        {
            switch (field) {
                case SUCCESS:
                    return getSuccess();
            }
            throw new IllegalStateException();
        }

        /**
         * Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise
         */
        public boolean isSet(_Fields field)
        {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case SUCCESS:
                    return isSetSuccess();
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean equals(Object that)
        {
            if (that == null) {
                return false;
            }
            if (that instanceof Log_result) {
                return this.equals((Log_result) that);
            }
            return false;
        }

        public boolean equals(Log_result that)
        {
            if (that == null) {
                return false;
            }
            if (this == that) {
                return true;
            }

            boolean this_present_success = true && this.isSetSuccess();
            boolean that_present_success = true && that.isSetSuccess();
            if (this_present_success || that_present_success) {
                if (!(this_present_success && that_present_success)) {
                    return false;
                }
                if (!this.success.equals(that.success)) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public int hashCode()
        {
            int hashCode = 1;

            hashCode = hashCode * 8191 + ((isSetSuccess()) ? 131071 : 524287);
            if (isSetSuccess()) {
                hashCode = hashCode * 8191 + success.getValue();
            }

            return hashCode;
        }

        @Override
        public int compareTo(Log_result other)
        {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;

            lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetSuccess()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId)
        {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot)
                throws TException
        {
            scheme(iprot).read(iprot, this);
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot)
                throws TException
        {
            scheme(oprot).write(oprot, this);
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder("Log_result(");
            boolean first = true;

            sb.append("success:");
            if (this.success == null) {
                sb.append("null");
            }
            else {
                sb.append(this.success);
            }
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate()
                throws TException
        {
            // check for required fields
            // check for sub-struct validity
        }

        private void writeObject(java.io.ObjectOutputStream out)
                throws java.io.IOException
        {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            }
            catch (TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in)
                throws java.io.IOException, ClassNotFoundException
        {
            try {
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            }
            catch (TException te) {
                throw new java.io.IOException(te);
            }
        }

        private static class Log_resultStandardSchemeFactory
                implements SchemeFactory
        {
            public Log_resultStandardScheme getScheme()
            {
                return new Log_resultStandardScheme();
            }
        }

        private static class Log_resultStandardScheme
                extends StandardScheme<Log_result>
        {
            public void read(org.apache.thrift.protocol.TProtocol iprot, Log_result struct)
                    throws TException
            {
                org.apache.thrift.protocol.TField schemeField;
                iprot.readStructBegin();
                while (true) {
                    schemeField = iprot.readFieldBegin();
                    if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                        break;
                    }
                    switch (schemeField.id) {
                        case 0: // SUCCESS
                            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                                struct.success = ResultCode.findByValue(iprot.readI32());
                                struct.setSuccessIsSet(true);
                            }
                            else {
                                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                            }
                            break;
                        default:
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    iprot.readFieldEnd();
                }
                iprot.readStructEnd();

                // check for required fields of primitive type, which can't be checked in the validate method
                struct.validate();
            }

            public void write(org.apache.thrift.protocol.TProtocol oprot, Log_result struct)
                    throws TException
            {
                struct.validate();

                oprot.writeStructBegin(STRUCT_DESC);
                if (struct.success != null) {
                    oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
                    oprot.writeI32(struct.success.getValue());
                    oprot.writeFieldEnd();
                }
                oprot.writeFieldStop();
                oprot.writeStructEnd();
            }
        }

        private static class Log_resultTupleSchemeFactory
                implements SchemeFactory
        {
            public Log_resultTupleScheme getScheme()
            {
                return new Log_resultTupleScheme();
            }
        }

        private static class Log_resultTupleScheme
                extends TupleScheme<Log_result>
        {
            @Override
            public void write(org.apache.thrift.protocol.TProtocol prot, Log_result struct)
                    throws TException
            {
                TTupleProtocol oprot = (TTupleProtocol) prot;
                BitSet optionals = new BitSet();
                if (struct.isSetSuccess()) {
                    optionals.set(0);
                }
                oprot.writeBitSet(optionals, 1);
                if (struct.isSetSuccess()) {
                    oprot.writeI32(struct.success.getValue());
                }
            }

            @Override
            public void read(org.apache.thrift.protocol.TProtocol prot, Log_result struct)
                    throws TException
            {
                TTupleProtocol iprot = (TTupleProtocol) prot;
                BitSet incoming = iprot.readBitSet(1);
                if (incoming.get(0)) {
                    struct.success = ResultCode.findByValue(iprot.readI32());
                    struct.setSuccessIsSet(true);
                }
            }
        }

        private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto)
        {
            return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
        }
    }
}
